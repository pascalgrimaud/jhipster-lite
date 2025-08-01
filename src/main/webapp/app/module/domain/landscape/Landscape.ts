import { LandscapeElement } from '@/module/domain/landscape/LandscapeElement';
import { LandscapeElementId } from '@/module/domain/landscape/LandscapeElementId';
import { LandscapeFeature } from '@/module/domain/landscape/LandscapeFeature';
import { LandscapeFeatureSlug } from '@/module/domain/landscape/LandscapeFeatureSlug';
import { LandscapeLevel } from '@/module/domain/landscape/LandscapeLevel';
import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';
import { LandscapeSelectionElement } from '@/module/domain/landscape/LandscapeSelectionElement';
import { LandscapeSelectionTree } from '@/module/domain/landscape/LandscapeSelectionTree';
import { LandscapeUnselectionTree } from '@/module/domain/landscape/LandscapeUnselectionTree';
import { ModuleRank } from '@/module/domain/landscape/ModuleRank';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { Memoizer } from '@/shared/memoizer/domain/Memoizer';
import { Optional } from '@/shared/optional/domain/Optional';

type ModuleId = string;

export class Landscape {
  private readonly modules: Map<string, LandscapeModule>;
  private readonly properties: ModulePropertyDefinition[];
  private readonly memoizedSelectionElements = new Memoizer<string, LandscapeSelectionElement[]>();

  private constructor(
    private readonly state: LandscapeState,
    private readonly projections: LevelsProjections,
  ) {
    this.modules = this.buildModules();
    this.properties = this.buildProperties();
  }

  private buildModules(): Map<string, LandscapeModule> {
    return this.projections.mapModules(module => this.moduleInContext(module));
  }

  private moduleInContext(module: LandscapeModule): LandscapeModule {
    const moduleId = module.slugString();

    return module.inContext({
      selected: this.state.isSelected(moduleId),
      applied: this.state.isApplied(moduleId),
      selectionTree: this.buildSelectionTree(module),
      unselectionTree: this.buildUnselectionTree(module),
      visible: module.isVisible(),
    });
  }

  private buildSelectionTree(module: LandscapeModule): LandscapeSelectionTree {
    const dependenciesSelection = new LandscapeSelectionTree(
      module.dependencies().flatMap(dependency => this.toSelectionElements(dependency)),
    );

    return new LandscapeSelectionTree([this.moduleSelection(module, dependenciesSelection), ...dependenciesSelection.elements]);
  }

  private toSelectionElements(dependency: LandscapeElementId): LandscapeSelectionElement[] {
    return this.memoizedSelectionElements.get(dependency.get(), () => {
      if (this.isModule(dependency)) {
        return this.moduleSelectionElements(dependency as ModuleSlug);
      }

      return this.featureSelectionElements(dependency);
    });
  }

  private isModule(dependency: LandscapeElementId): boolean {
    return dependency instanceof ModuleSlug;
  }

  private moduleSelectionElements(dependency: ModuleSlug): LandscapeSelectionElement[] {
    return this.incompatibleSelectedModuleInFeature(dependency)
      .map(incompatibleModule => this.toFeatureSelectionWithIncompatibleModule(dependency, incompatibleModule))
      .orElseGet(() => this.selectableModuleSelections(dependency));
  }

  private moduleSelection(module: LandscapeModule, dependenciesSelection: LandscapeSelectionTree): LandscapeSelectionElement {
    return {
      slug: module.slug(),
      selectable: this.isModuleSelectable(dependenciesSelection, module),
    };
  }

  private isModuleSelectable(dependenciesSelection: LandscapeSelectionTree, module: LandscapeModule): boolean {
    return (
      dependenciesSelection.isSelectable()
      && this.hasNoIncompatibleSelectedModuleInFeature(module.slug())
      && this.hasNoIncompatibleDependency(module)
    );
  }

  private hasNoIncompatibleSelectedModuleInFeature(module: ModuleSlug): boolean {
    return this.hasNoIncompatibleAppliedModule(module) && this.incompatibleSelectedModuleInFeature(module).isEmpty();
  }

  private hasNoIncompatibleAppliedModule(module: ModuleSlug) {
    return this.appliedModuleInFeature(module)
      .map(appliedModule => appliedModule.slugString() === module.get())
      .orElse(true);
  }

  private appliedModuleInFeature(module: ModuleSlug): Optional<LandscapeModule> {
    return this.projections
      .getModuleFeature(module)
      .flatMap(feature => Optional.ofNullable(feature.modules.find(featureModule => this.state.isApplied(featureModule.slugString()))));
  }

  private incompatibleSelectedModuleInFeature(module: ModuleSlug): Optional<LandscapeModule> {
    return this.selectedModuleInFeature(module)
      .filter(selectedModule => selectedModule.slugString() !== module.get())
      .filter(selectedFeatureModule => this.selectedModuleDependsOn(selectedFeatureModule));
  }

  private selectedModuleDependsOn(selectedFeatureModule: LandscapeModule): boolean {
    return this.state.selectedModules.some(selectedModule =>
      this.projections
        .getStandaloneModule(selectedModule)
        .map(standaloneModule =>
          standaloneModule.dependencies().some(dependency => dependency.get() === selectedFeatureModule.slug().get()),
        )
        .orElse(false),
    );
  }

  private hasNoIncompatibleDependency(module: LandscapeModule): boolean {
    return module.dependencies().every(dependency => {
      if (dependency instanceof LandscapeFeatureSlug) {
        return true;
      }

      return this.hasNoIncompatibleSelectedModule(dependency);
    });
  }

  private hasNoIncompatibleSelectedModule(dependency: ModuleSlug): unknown {
    return this.projections
      .getModuleFeature(dependency)
      .flatMap(feature => this.getSelectedModule(feature).map(selectedModule => dependency.get() === selectedModule.slugString()))
      .orElse(true);
  }

  private toFeatureSelectionWithIncompatibleModule(
    dependency: ModuleSlug,
    incompatibleModule: LandscapeModule,
  ): LandscapeSelectionElement[] {
    const notSelectableDependencySelection: LandscapeSelectionElement = {
      slug: dependency,
      selectable: false,
    };

    const incompatibleModuleSelection: LandscapeSelectionElement = {
      slug: incompatibleModule.slug(),
      selectable: false,
    };

    return [notSelectableDependencySelection, incompatibleModuleSelection, ...this.nestedDependencySelectionElements(dependency)];
  }

  private selectableModuleSelections(dependency: ModuleSlug): LandscapeSelectionElement[] {
    const selectableCurrentDependencySelection = {
      slug: dependency,
      selectable: true,
    };

    return [selectableCurrentDependencySelection, ...this.nestedDependencySelectionElements(dependency)];
  }

  private featureSelectionElements(dependency: LandscapeElementId): LandscapeSelectionElement[] {
    return this.getSelectableModuleInFeature(dependency as LandscapeFeatureSlug)
      .map(featureModule => this.buildFeatureSelection(dependency, featureModule))
      .orElseGet(() => this.notSelectableDependency(dependency));
  }

  private getSelectableModuleInFeature(featureSlug: LandscapeFeatureSlug): Optional<LandscapeModule> {
    return this.projections.getFeature(featureSlug).flatMap(feature => {
      const featureModules = feature.modules;

      if (featureModules.length === 1) {
        return Optional.of(featureModules[0]);
      }

      return this.getSelectedModule(feature);
    });
  }

  private buildFeatureSelection(dependency: LandscapeElementId, featureModule: LandscapeModule): LandscapeSelectionElement[] {
    const selectableDependencySelection = {
      slug: dependency,
      selectable: true,
    };

    const selectedModuleSelection = {
      slug: featureModule.slug(),
      selectable: true,
    };

    return [selectableDependencySelection, selectedModuleSelection, ...this.nestedDependencySelectionElements(featureModule.slug())];
  }

  private nestedDependencySelectionElements(dependency: ModuleSlug): LandscapeSelectionElement[] {
    return this.projections
      .getStandaloneModule(dependency.get())
      .map(standaloneModule =>
        standaloneModule
          .allModules()
          .flatMap(dependencyModules => dependencyModules.dependencies())
          .flatMap(moduleDependency => this.toSelectionElements(moduleDependency)),
      )
      .orElse([]);
  }

  private notSelectableDependency(dependency: LandscapeElementId): LandscapeSelectionElement[] {
    const notSelectableDependencySelection = {
      slug: dependency,
      selectable: false,
    };

    return [notSelectableDependencySelection];
  }

  private buildUnselectionTree(module: LandscapeModule): LandscapeUnselectionTree {
    return new LandscapeUnselectionTree(this.selectedDependantElements(module));
  }

  private selectedDependantElements(module: LandscapeModule): LandscapeElementId[] {
    const dependantElements = [
      ...this.moduleSelectedElements(module),
      ...this.selectedModuleSlugsInFeature(module),
      ...this.nestedSelectedDependantElements(module.slug()),
    ];

    return [...new Map(dependantElements.map(dependantElement => [dependantElement.get(), dependantElement])).values()];
  }

  private moduleSelectedElements(module: LandscapeModule): LandscapeElementId[] {
    const slug = module.slug();
    if (this.state.isNotSelected(slug.get())) {
      return [];
    }

    return [slug];
  }

  private selectedModuleSlugsInFeature(module: LandscapeModule) {
    return this.selectedModuleInFeature(module.slug())
      .map(dependantModule => dependantModule.slug())
      .toArray();
  }

  private selectedModuleInFeature(module: ModuleSlug): Optional<LandscapeModule> {
    return this.projections.getModuleFeature(module).flatMap(feature => this.getSelectedModule(feature));
  }

  private nestedSelectedDependantElements(element: LandscapeElementId): LandscapeElementId[] {
    const selectedDependantElements = this.getAllDependantModules(element).filter(dependantElement =>
      this.isSelectedModuleOrFeature(dependantElement),
    );

    return [
      ...selectedDependantElements,
      ...selectedDependantElements.flatMap(dependantElement => this.nestedSelectedDependantElements(dependantElement)),
    ];
  }

  private isSelectedModuleOrFeature(dependantElement: LandscapeElementId): boolean {
    return this.state.isSelected(dependantElement.get()) || dependantElement instanceof LandscapeFeatureSlug;
  }

  private getAllDependantModules(element: LandscapeElementId): LandscapeElementId[] {
    return [...this.projections.getDependantModules(element), ...this.getFeatureDependantModules(element)];
  }

  private getFeatureDependantModules(element: LandscapeElementId): LandscapeElementId[] {
    if (element instanceof LandscapeFeatureSlug) {
      return [];
    }

    return this.projections
      .getModuleFeature(element)
      .filter(feature => this.currentSelectedModule(feature, element))
      .map(feature => [feature.slug(), ...this.projections.getDependantModules(feature.slug())])
      .orElse([]);
  }

  private currentSelectedModule(feature: LandscapeFeature, element: LandscapeElementId): boolean {
    return this.getSelectedModule(feature)
      .map(selectedModule => selectedModule.slug().get() === element.get())
      .orElse(false);
  }

  private buildProperties(): ModulePropertyDefinition[] {
    const deduplicatedProperties = Array.from(
      new Map(
        this.state.selectedModules.flatMap(module => this.toPropertiesDefinitions(module)).map(property => [property.key, property]),
      ).values(),
    );

    return deduplicatedProperties.sort((first, second) => first.order - second.order);
  }

  private toPropertiesDefinitions(module: string): ModulePropertyDefinition[] {
    return this.projections
      .getStandaloneModule(module)
      .map(standaloneModule => standaloneModule.properties())
      .orElse([]);
  }

  static initialState(levels: LandscapeLevel[]): Landscape {
    return new Landscape(new LandscapeState([], []), new LevelsProjections(levels));
  }

  standaloneLevels(): LandscapeLevel[] {
    return this.filterVisibleLandscapeLevel();
  }

  private filterVisibleLandscapeLevel() {
    return this.projections.levels
      .map(level => ({
        elements: level.elements.flatMap(element =>
          Optional.of(element)
            .filter(e => e.isVisible())
            .flatMap(e => this.visibleLandscapeElement(e))
            .toArray(),
        ),
      }))
      .filter(level => level.elements.length > 0);
  }

  private visibleLandscapeElement(element: LandscapeElement): Optional<LandscapeElement> {
    if (element instanceof LandscapeFeature) {
      return Optional.of(element.modules)
        .map(modules => modules.filter(module => module.isVisible()))
        .filter(visibleModules => visibleModules.length > 0)
        .map(visibleModules => new LandscapeFeature(element.slug(), visibleModules, element.isVisible()));
    }
    return Optional.of(element);
  }

  resetAppliedModules(appliedModules: ModuleSlug[]): Landscape {
    return new Landscape(this.state.resetAppliedModules(appliedModules), this.projections);
  }

  appliedModules(appliedModules: ModuleSlug[]): Landscape {
    return new Landscape(this.state.addAppliedModules(appliedModules), this.projections);
  }

  toggle(module: ModuleSlug): Landscape {
    if (this.isNotSelectable(module)) {
      return this;
    }

    return this.getModule(module)
      .map(currentModule => new Landscape(this.state.toggle(currentModule), this.projections))
      .orElse(this);
  }

  private isNotSelectable(module: ModuleSlug): boolean {
    return !this.isSelectable(module);
  }

  isSelectable(module: ModuleSlug): boolean {
    return this.moduleIs(module, currentModule => currentModule.selectionTree().isSelectable());
  }

  isApplied(module: ModuleSlug): boolean {
    return this.moduleIs(module, currentModule => currentModule.isApplied());
  }

  isSelected(element: LandscapeElementId): boolean {
    if (element instanceof ModuleSlug) {
      return this.moduleIs(element, currentModule => currentModule.isSelected());
    }

    return this.projections
      .getFeature(element)
      .map(feature => this.getSelectedModule(feature).isPresent())
      .orElse(false);
  }

  private getSelectedModule(feature: LandscapeFeature): Optional<LandscapeModule> {
    return Optional.ofNullable(feature.modules.find(featureModule => this.state.isSelected(featureModule.slug().get())));
  }

  private moduleIs(slug: ModuleSlug, operation: (module: LandscapeModule) => boolean): boolean {
    return this.getModule(slug).map(operation).orElse(false);
  }

  selectionTreeFor(module: ModuleSlug): LandscapeSelectionTree {
    return this.getModule(module)
      .map(currentModule => currentModule.selectionTree())
      .orElse(LandscapeSelectionTree.EMPTY);
  }

  unselectionTreeFor(module: ModuleSlug): LandscapeUnselectionTree {
    return this.getModule(module)
      .map(currentModule => currentModule.unselectionTree())
      .orElse(LandscapeUnselectionTree.EMPTY);
  }

  private getModule(module: ModuleSlug): Optional<LandscapeModule> {
    return Optional.ofNullable(this.modules.get(module.get()));
  }

  noNotAppliedSelectedModule(): boolean {
    return this.notAppliedSelectedModulesCount() === 0;
  }

  notAppliedSelectedModulesCount(): number {
    return this.getNotAppliedSelectedModuleIds().length;
  }

  notAppliedSelectedModules(): ModuleSlug[] {
    return this.getNotAppliedSelectedModuleIds().map(slug => new ModuleSlug(slug));
  }

  private getNotAppliedSelectedModuleIds() {
    return this.state.selectedModules.filter(selectedModule => !this.state.appliedModules.includes(selectedModule));
  }

  noSelectedModule(): boolean {
    return this.selectedModulesCount() === 0;
  }

  selectedModulesCount(): number {
    return this.state.selectedModules.length;
  }

  selectedModules(): ModuleSlug[] {
    return this.state.selectedModules.map(slug => new ModuleSlug(slug));
  }

  selectedModulesProperties(): ModulePropertyDefinition[] {
    return this.properties;
  }

  getModuleRank(module: ModuleSlug): Optional<ModuleRank> {
    return this.getModule(module).map(currentModule => currentModule.rank());
  }

  hasModuleDifferentRank(module: ModuleSlug, rank: ModuleRank): boolean {
    return this.getModule(module)
      .map(currentModule => currentModule.rank() !== rank)
      .orElse(false);
  }

  filterByRank(rank: Optional<ModuleRank>): Landscape {
    const fullyVisibleLandscape = new Landscape(this.state, new LevelsProjections(this.createLevelsWithVisibleElements()));

    return rank.map(currentRank => fullyVisibleLandscape.createFilteredLandscape(currentRank)).orElse(fullyVisibleLandscape);
  }

  private createLevelsWithVisibleElements() {
    return this.projections.levels.map(level => ({
      elements: level.elements.map(element => element.withAllVisibility(true)),
    }));
  }

  private createFilteredLandscape(rank: ModuleRank): Landscape {
    const filteredLevels = this.projections.levels.map(level => ({
      elements: level.elements.map(element => this.setElementVisibility(element, rank)),
    }));

    return new Landscape(this.state, new LevelsProjections(filteredLevels));
  }

  private setElementVisibility(element: LandscapeElement, rank: ModuleRank): LandscapeElement {
    if (element instanceof LandscapeFeature) {
      return this.setFeatureVisibility(element, rank);
    }
    return this.setModuleVisibility(<LandscapeModule>element, rank);
  }

  private setFeatureVisibility(feature: LandscapeFeature, rank: ModuleRank): LandscapeFeature {
    if (this.dependencyFeatureOfRankedModule(feature.slug(), rank)) {
      return feature;
    }

    return Optional.of(feature.modules)
      .map(this.updateModulesVisibility(rank))
      .filter(this.hasVisibleModules)
      .map(modules => new LandscapeFeature(feature.slug(), modules, true))
      .orElse(new LandscapeFeature(feature.slug(), feature.modules, false));
  }

  private updateModulesVisibility(rank: ModuleRank): (modules: LandscapeModule[]) => LandscapeModule[] {
    return modules => modules.map(module => module.withAllVisibility(this.moduleMatchingRank(module, rank)) as LandscapeModule);
  }

  private hasVisibleModules(modules: LandscapeModule[]): boolean {
    return modules.some(module => module.isVisible());
  }

  private setModuleVisibility(module: LandscapeModule, rank: ModuleRank): LandscapeElement {
    return module.withAllVisibility(this.moduleMatchingRank(module, rank));
  }

  private dependencyFeatureOfRankedModule(featureSlug: LandscapeFeatureSlug, rank: ModuleRank): boolean {
    return Array.from(this.modules.values())
      .filter(module => module.rank() === rank)
      .some(rankedModule => this.featureDependencyOf(featureSlug, rankedModule));
  }

  private featureDependencyOf(featureSlug: LandscapeFeatureSlug, module: LandscapeModule): boolean {
    return this.dependencyOf(featureSlug, module);
  }

  private dependencyOf(dependencyId: LandscapeElementId, module: LandscapeModule): boolean {
    if (this.directDependency(module, dependencyId)) {
      return true;
    }

    return this.nestedDependency(module, dependencyId);
  }

  private directDependency(module: LandscapeModule, dependencyId: LandscapeFeatureSlug | ModuleSlug) {
    return module.dependencies().some(dep => dep.get() === dependencyId.get());
  }

  private nestedDependency(module: LandscapeModule, dependencyId: LandscapeFeatureSlug | ModuleSlug) {
    return module.dependencies().some(dep => {
      if (dep instanceof ModuleSlug) {
        return this.getModule(dep)
          .map(depModule => this.dependencyOf(dependencyId, depModule))
          .orElse(false);
      }

      return this.projections
        .getFeature(dep)
        .map(feature => feature.modules.some(featureModule => this.dependencyOf(dependencyId, featureModule)))
        .orElse(false);
    });
  }

  private moduleMatchingRank(module: LandscapeModule, rank: ModuleRank): boolean {
    return module.rank() === rank || this.dependencyOfRankedModule(module, rank);
  }

  private dependencyOfRankedModule(module: LandscapeModule, rank: ModuleRank): boolean {
    return Optional.of(Array.from(this.modules.values()))
      .map(modules => modules.filter(m => m.rank() === rank))
      .map(rankedModules => rankedModules.some(rankedModule => this.dependencyOf(module.slug(), rankedModule)))
      .orElse(false);
  }
}

class LandscapeState {
  constructor(
    readonly appliedModules: ModuleId[],
    readonly selectedModules: ModuleId[],
  ) {}

  isApplied(module: ModuleId): boolean {
    return this.isModuleIn(module, this.appliedModules);
  }

  toggle(module: LandscapeModule): LandscapeState {
    if (this.isSelected(module.slugString())) {
      return this.unselectModule(module);
    }

    return this.selectModule(module);
  }

  private unselectModule(module: LandscapeModule): LandscapeState {
    return new LandscapeState(this.appliedModules, this.selectedModuleAfterUnselection(module));
  }

  private selectModule(module: LandscapeModule): LandscapeState {
    const moduleSelectionTree = module
      .selectionTree()
      .elements.map(element => element.slug)
      .filter(element => element instanceof ModuleSlug)
      .map(slug => slug.get());

    const modulesToSelect = [...this.selectedModuleAfterUnselection(module), ...moduleSelectionTree];
    return new LandscapeState(this.appliedModules, [...new Set(modulesToSelect)]);
  }

  private selectedModuleAfterUnselection(module: LandscapeModule): ModuleId[] {
    const modulesToUnselect = module
      .unselectionTree()
      .elements.filter(element => element instanceof ModuleSlug)
      .map(element => element.get());

    return this.selectedModules.filter(selectedModule => !modulesToUnselect.includes(selectedModule));
  }

  isNotSelected(module: ModuleId): boolean {
    return !this.isSelected(module);
  }

  isSelected(module: ModuleId): boolean {
    return this.isModuleIn(module, this.selectedModules);
  }

  private isModuleIn(module: ModuleId, modules: ModuleId[]): boolean {
    return modules.includes(module);
  }

  resetAppliedModules(appliedModules: ModuleSlug[]): LandscapeState {
    const moduleIds = appliedModules.map(module => module.get());

    return new LandscapeState(moduleIds, [...new Set([...moduleIds, ...this.selectedModules])]);
  }

  addAppliedModules(appliedModules: ModuleSlug[]): LandscapeState {
    const moduleIds = appliedModules.map(module => module.get());

    return new LandscapeState([...this.appliedModules, ...moduleIds], [...new Set([...moduleIds, ...this.selectedModules])]);
  }
}

class LevelsProjections {
  private readonly features: Map<string, LandscapeFeature>;
  private readonly moduleFeatures: Map<string, LandscapeFeature>;
  private readonly standaloneModules: Map<ModuleId, LandscapeModule>;

  private readonly memoizedDependantModules = new Memoizer<string, ModuleSlug[]>();

  constructor(readonly levels: LandscapeLevel[]) {
    this.features = this.buildFeatures(levels);
    this.moduleFeatures = this.buildModuleFeatures();
    this.standaloneModules = this.buildStandaloneModules(levels);
  }

  private buildFeatures(levels: LandscapeLevel[]): Map<string, LandscapeFeature> {
    return new Map(
      levels
        .flatMap(level => level.elements)
        .filter(element => element instanceof LandscapeFeature)
        .map(feature => [feature.slugString(), feature]),
    );
  }

  private buildModuleFeatures(): Map<string, LandscapeFeature> {
    return new Map(
      Array.from(this.features.values()).flatMap(feature => feature.allModules().map(module => [module.slugString(), feature])),
    );
  }

  private buildStandaloneModules(levels: LandscapeLevel[]): Map<ModuleId, LandscapeModule> {
    return new Map(
      levels
        .flatMap(level => level.elements)
        .flatMap(landscapeElement => landscapeElement.allModules())
        .map(module => [module.slugString(), module]),
    );
  }

  getDependantModules(element: LandscapeElementId): ModuleSlug[] {
    return this.memoizedDependantModules.get(element.get(), () =>
      Array.from(this.standaloneModules.values())
        .filter(standaloneModule => standaloneModule.dependencies().some(dependency => dependency.get() === element.get()))
        .map(module => module.slug()),
    );
  }

  mapModules(mapper: (module: LandscapeModule) => LandscapeModule): Map<string, LandscapeModule> {
    return new Map(Array.from(this.standaloneModules.entries()).map(entry => [entry[0], mapper(entry[1])]));
  }

  getStandaloneModule(module: ModuleId): Optional<LandscapeModule> {
    return Optional.ofNullable(this.standaloneModules.get(module));
  }

  getFeature(feature: LandscapeFeatureSlug): Optional<LandscapeFeature> {
    return Optional.ofNullable(this.features.get(feature.get()));
  }

  getModuleFeature(module: ModuleSlug) {
    return Optional.ofNullable(this.moduleFeatures.get(module.get()));
  }
}

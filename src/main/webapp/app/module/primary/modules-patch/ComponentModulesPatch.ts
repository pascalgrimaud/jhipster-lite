import { Modules } from '@/module/domain/Modules';
import { ComponentModuleCategory } from './ComponentModulePatchCategory';

export class ComponentModules {
  private constructor(
    readonly categories: ComponentModuleCategory[],
    readonly modulesCount: number,
  ) {}

  static fromModules = (modules: Modules): ComponentModules =>
    new ComponentModules(
      modules.categories.map(category => ComponentModuleCategory.from(category)),
      modules.modulesCount(),
    );

  filtered(search: string): ComponentModules {
    const filteredCategories = this.filterCategories(search);

    return new ComponentModules(filteredCategories, this.countModules(filteredCategories));
  }

  private filterCategories = (search: string): ComponentModuleCategory[] =>
    this.categories.map(category => this.toFilteredCategory(category, search)).filter(category => category.modules.length !== 0);

  private toFilteredCategory(category: ComponentModuleCategory, search: string): ComponentModuleCategory {
    return {
      name: category.name,
      modules: category.modules.filter(module => {
        return search.split(' ').every(term => this.contains(module.normalizedContent, term));
      }),
    };
  }

  private contains = (value: string, search: string): boolean => value.indexOf(search) !== -1;

  private countModules = (filteredCategories: ComponentModuleCategory[]): number =>
    filteredCategories.map(category => category.modules.length).reduce((previous, current) => previous + current, 0);
}

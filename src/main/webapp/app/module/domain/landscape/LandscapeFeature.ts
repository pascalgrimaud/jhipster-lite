import { LandscapeElement } from '@/module/domain/landscape/LandscapeElement';
import { LandscapeFeatureSlug } from '@/module/domain/landscape/LandscapeFeatureSlug';
import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';

export class LandscapeFeature implements LandscapeElement {
  private readonly visible: boolean;

  constructor(
    private readonly featureSlug: LandscapeFeatureSlug,
    readonly modules: LandscapeModule[],
    visible: boolean = true,
  ) {
    this.visible = visible;
  }

  slugString(): string {
    return this.slug().get();
  }

  slug(): LandscapeFeatureSlug {
    return this.featureSlug;
  }

  allModules(): LandscapeModule[] {
    return this.modules;
  }

  isVisible(): boolean {
    return this.visible;
  }

  withAllVisibility(visible: boolean): LandscapeElement {
    const restoredModules = this.allModules().map(module => module.withAllVisibility(visible) as LandscapeModule);
    return new LandscapeFeature(this.slug(), restoredModules, visible);
  }
}

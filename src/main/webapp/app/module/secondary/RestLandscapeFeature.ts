import { LandscapeElementType } from '../domain/landscape/LandscapeElementType';
import { LandscapeFeature } from '../domain/landscape/LandscapeFeature';
import { LandscapeFeatureSlug } from '../domain/landscape/LandscapeFeatureSlug';
import { RestLandscapeModule, toLandscapeModule } from './RestLandscapeModule';

export interface RestLandscapeFeature {
  type: LandscapeElementType;
  slug: string;
  modules: RestLandscapeModule[];
}

export const toLandscapeFeature = (feature: RestLandscapeFeature): LandscapeFeature =>
  new LandscapeFeature(new LandscapeFeatureSlug(feature.slug), feature.modules.map(toLandscapeModule));

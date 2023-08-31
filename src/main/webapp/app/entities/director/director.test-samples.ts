import { IDirector, NewDirector } from './director.model';

export const sampleWithRequiredData: IDirector = {
  id: '8ba9830b-917a-4704-922a-fd822711ea3f',
  directorName: 'violet',
};

export const sampleWithPartialData: IDirector = {
  id: '14dcf313-93af-4eef-8b25-d6655618af84',
  directorName: 'Integration payment',
  description: 'bluetooth clear-thinking relationships',
};

export const sampleWithFullData: IDirector = {
  id: '5fd3068d-9b2b-4553-ba91-3ed40480e870',
  directorName: 'protocol',
  description: 'Chips Diverse',
};

export const sampleWithNewData: NewDirector = {
  directorName: 'high-level repurpose Alabama',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { ITeam, NewTeam } from './team.model';

export const sampleWithRequiredData: ITeam = {
  id: 'e7efbde1-fad0-4d2f-bbc5-0e868b2a35f6',
};

export const sampleWithPartialData: ITeam = {
  id: '7a89586e-9d9c-45fd-a806-c1ea897cb888',
  managerialId: 67481,
};

export const sampleWithFullData: ITeam = {
  id: '8de85018-42e1-4c51-b635-29d225cab1c9',
  teamName: 'Fresh Estate',
  description: 'array',
  managerialId: 22317,
  isCreator: true,
};

export const sampleWithNewData: NewTeam = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

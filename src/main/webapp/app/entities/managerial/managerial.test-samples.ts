import { IManagerial, NewManagerial } from './managerial.model';

export const sampleWithRequiredData: IManagerial = {
  id: '2489a07e-4f24-4fbd-bc84-59bed55c9f5b',
};

export const sampleWithPartialData: IManagerial = {
  id: '93fc4e6c-f5ac-4483-85f3-43924c8cee79',
  managerialName: 'Consultant Cambridgeshire AGP',
  directorId: 70377,
};

export const sampleWithFullData: IManagerial = {
  id: '81700304-5c9a-4c8c-9a2f-37a24009f86c',
  managerialName: 'Borders',
  description: 'Market',
  directorId: 21697,
};

export const sampleWithNewData: NewManagerial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

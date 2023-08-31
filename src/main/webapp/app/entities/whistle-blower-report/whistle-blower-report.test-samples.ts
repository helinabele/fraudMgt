import { Gender } from 'app/entities/enumerations/gender.model';

import { IWhistleBlowerReport, NewWhistleBlowerReport } from './whistle-blower-report.model';

export const sampleWithRequiredData: IWhistleBlowerReport = {
  id: 'b6ffc0fc-399d-4530-8ceb-db41224a95a2',
};

export const sampleWithPartialData: IWhistleBlowerReport = {
  id: '79f605c4-09e7-4514-909f-d47fc331da31',
  emailAdress: 'interactive Officer',
  organization: 'panel strategize',
  attachment: '../fake-data/blob/hipster.png',
  attachmentContentType: 'unknown',
};

export const sampleWithFullData: IWhistleBlowerReport = {
  id: 'b1f66361-35ce-4e59-84da-e1ee4917d362',
  fullName: 'magenta parsing Director',
  genderType: Gender['OTHER'],
  emailAdress: 'Mexico calculating Pants',
  phone: 52038,
  organization: 'networks Open-architected',
  message: 'schemas',
  attachment: '../fake-data/blob/hipster.png',
  attachmentContentType: 'unknown',
};

export const sampleWithNewData: NewWhistleBlowerReport = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

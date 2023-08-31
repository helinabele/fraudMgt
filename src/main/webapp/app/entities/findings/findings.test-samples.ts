import { IFindings, NewFindings } from './findings.model';

export const sampleWithRequiredData: IFindings = {
  id: '6d0bed4b-63a1-4619-9a47-356b2e5887f3',
};

export const sampleWithPartialData: IFindings = {
  id: '62a52dbb-abf4-4b18-a19b-97f054537a5a',
  findingAndAnalysisAnnex: '../fake-data/blob/hipster.png',
  findingAndAnalysisAnnexContentType: 'unknown',
};

export const sampleWithFullData: IFindings = {
  id: 'f9ccf28d-8a3d-46f3-8803-2c6020fc7970',
  findingAndAnalysis: 'Colorado bottom-line emulation',
  findingAndAnalysisAnnex: '../fake-data/blob/hipster.png',
  findingAndAnalysisAnnexContentType: 'unknown',
};

export const sampleWithNewData: NewFindings = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

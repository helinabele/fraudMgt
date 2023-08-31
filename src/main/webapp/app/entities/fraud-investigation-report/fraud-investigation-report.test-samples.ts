import dayjs from 'dayjs/esm';

import { IFraudInvestigationReport, NewFraudInvestigationReport } from './fraud-investigation-report.model';

export const sampleWithRequiredData: IFraudInvestigationReport = {
  id: '785f0698-481f-4d76-9cd2-cbb48dba68c1',
  title: 'De-engineered Granite application',
  introduction: 'Practical Assistant Kuwaiti',
  objective: 'Pants Congolese firewall',
  methodology: 'discrete Licensed hacking',
};

export const sampleWithPartialData: IFraudInvestigationReport = {
  id: 'd51dcc38-a62a-49eb-b0a7-372a01edf9d8',
  title: 'Island',
  introductionAnnex: '../fake-data/blob/hipster.png',
  introductionAnnexContentType: 'unknown',
  introduction: 'Isle Operations',
  objective: 'Incredible lime Chair',
  limitation: 'Maine',
  limitationAnnex: '../fake-data/blob/hipster.png',
  limitationAnnexContentType: 'unknown',
  methodology: 'Bacon 24/7',
  methodologyAnnex: '../fake-data/blob/hipster.png',
  methodologyAnnexContentType: 'unknown',
  findingAndAnalysis: 'firewall invoice encoding',
  findingAndAnalysisAnnex: '../fake-data/blob/hipster.png',
  findingAndAnalysisAnnexContentType: 'unknown',
  conclusion: 'Usability Borders Arkansas',
  recommendation: 'Morocco TCP',
  recommendationAnnex: '../fake-data/blob/hipster.png',
  recommendationAnnexContentType: 'unknown',
  nameOfMembers: 'maximize Cambridgeshire',
  author: 'impactful scale',
};

export const sampleWithFullData: IFraudInvestigationReport = {
  id: '576f1e78-ae5f-4a9e-9b19-d23f225613ab',
  title: 'JBOD bypassing',
  executiveSummary: 'Mouse Leu Account',
  introductionAnnex: '../fake-data/blob/hipster.png',
  introductionAnnexContentType: 'unknown',
  introduction: 'Factors Awesome',
  objective: 'Cliffs Clothing back-end',
  objectiveAnnex: '../fake-data/blob/hipster.png',
  objectiveAnnexContentType: 'unknown',
  scope: 'York',
  scopeAnnex: '../fake-data/blob/hipster.png',
  scopeAnnexContentType: 'unknown',
  limitation: 'Technician synthesize',
  limitationAnnex: '../fake-data/blob/hipster.png',
  limitationAnnexContentType: 'unknown',
  methodology: 'Administrator',
  methodologyAnnex: '../fake-data/blob/hipster.png',
  methodologyAnnexContentType: 'unknown',
  findingAndAnalysis: 'Mexican Port blue',
  findingAndAnalysisAnnex: '../fake-data/blob/hipster.png',
  findingAndAnalysisAnnexContentType: 'unknown',
  conclusion: 'Handcrafted',
  conclusionAnnex: '../fake-data/blob/hipster.png',
  conclusionAnnexContentType: 'unknown',
  recommendation: 'port',
  recommendationAnnex: '../fake-data/blob/hipster.png',
  recommendationAnnexContentType: 'unknown',
  nameOfMembers: 'azure Indian',
  signature: 'Wisconsin Down-sized',
  references: '../fake-data/blob/hipster.png',
  referencesContentType: 'unknown',
  publicationDate: dayjs('2023-08-25T07:19'),
  author: 'Cotton',
};

export const sampleWithNewData: NewFraudInvestigationReport = {
  title: 'reinvent',
  introduction: 'initiative Handcrafted Falls',
  objective: 'world-class',
  methodology: 'Planner',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

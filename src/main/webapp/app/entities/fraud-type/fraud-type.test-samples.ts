import { IFraudType, NewFraudType } from './fraud-type.model';

export const sampleWithRequiredData: IFraudType = {
  id: 'b27b8f1c-87db-4d31-b7ea-2912267ca270',
};

export const sampleWithPartialData: IFraudType = {
  id: '77a188d2-a6fa-48d2-94a8-826863a1b1ce',
  fraudName: 'Borders matrix efficient',
  attachment: '../fake-data/blob/hipster.png',
  attachmentContentType: 'unknown',
};

export const sampleWithFullData: IFraudType = {
  id: '75464442-c359-4d83-9ebe-4d3ec69b8f6b',
  fraudName: 'Avon',
  description: 'hardware Cote',
  attachment: '../fake-data/blob/hipster.png',
  attachmentContentType: 'unknown',
};

export const sampleWithNewData: NewFraudType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

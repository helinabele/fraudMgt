import { IBankService, NewBankService } from './bank-service.model';

export const sampleWithRequiredData: IBankService = {
  id: '943265fb-39cb-4a54-8067-c8f1caaf0943',
  serviceName: 'lavender Berkshire Innovative',
};

export const sampleWithPartialData: IBankService = {
  id: 'dd5236ae-24de-49dd-a950-fd4d4d7c881f',
  serviceName: 'back Checking',
  description: 'Chicken',
};

export const sampleWithFullData: IBankService = {
  id: '09bd6b63-1132-48a2-9399-918c5f05ec66',
  serviceName: 'Arkansas Cambridgeshire',
  description: 'Cedi Business-focused implement',
};

export const sampleWithNewData: NewBankService = {
  serviceName: 'Fantastic invoice solutions',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

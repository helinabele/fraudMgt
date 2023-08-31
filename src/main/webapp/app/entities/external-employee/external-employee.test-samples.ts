import { IExternalEmployee, NewExternalEmployee } from './external-employee.model';

export const sampleWithRequiredData: IExternalEmployee = {
  id: '7dc47104-aa52-4d41-a888-10d8e631cc5a',
  name: 'Account Ways Diverse',
  address: 'Kentucky Direct',
};

export const sampleWithPartialData: IExternalEmployee = {
  id: '1da57073-6e84-4903-8780-4b15bf8e0039',
  name: 'Guyana AGP Savings',
  occupation: 'Fresh U.S.',
  address: 'leverage PNG',
};

export const sampleWithFullData: IExternalEmployee = {
  id: '94a640b5-9d7d-4fbe-b694-dc03e8e50bcf',
  name: 'Central Ngultrum',
  occupation: 'Sports',
  telephone: '1-929-835-3139',
  address: 'Throughway',
};

export const sampleWithNewData: NewExternalEmployee = {
  name: 'wireless SSL',
  address: 'New matrix',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

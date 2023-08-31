import { IInternalEmployee, NewInternalEmployee } from './internal-employee.model';

export const sampleWithRequiredData: IInternalEmployee = {
  id: '6ea8418f-1752-47ca-888a-362a1ed81076',
  name: 'Steel',
  position: 'Expanded channels Health',
};

export const sampleWithPartialData: IInternalEmployee = {
  id: 'ba5edc9d-9274-48ce-ba64-34385a38fd8c',
  name: 'Loan Florida',
  position: 'vertical algorithm efficient',
  experience: 'Ville withdrawal Facilitator',
  branch: 'CSS',
};

export const sampleWithFullData: IInternalEmployee = {
  id: '3fcd7b22-bb2d-4103-960b-21171d67483b',
  name: 'deposit invoice Mozambique',
  position: 'circuit Computer',
  grade: 'Chicken Architect Avon',
  experience: 'cultivate Cotton',
  branch: 'Engineer Fresh',
};

export const sampleWithNewData: NewInternalEmployee = {
  name: 'Trail Account',
  position: 'object-oriented Operations',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

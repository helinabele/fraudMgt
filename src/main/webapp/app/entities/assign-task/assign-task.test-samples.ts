import dayjs from 'dayjs/esm';

import { IAssignTask, NewAssignTask } from './assign-task.model';

export const sampleWithRequiredData: IAssignTask = {
  id: '1f3a18e1-b68a-49fe-bba8-5ae63612b2cd',
};

export const sampleWithPartialData: IAssignTask = {
  id: 'b00915c7-b708-472c-b3de-9c1916829cb2',
  taskAssignmentDate: dayjs('2023-08-25T12:02'),
  taskStartDate: dayjs('2023-08-24T12:40'),
  taskEndDate: dayjs('2023-08-24T16:25'),
  attachment: '../fake-data/blob/hipster.png',
  attachmentContentType: 'unknown',
  description: 'Car optimal Car',
};

export const sampleWithFullData: IAssignTask = {
  id: '702b329e-af5d-4bd3-9e04-668f061cd16f',
  taskAssignmentDate: dayjs('2023-08-24T16:26'),
  taskStartDate: dayjs('2023-08-24T23:53'),
  taskEndDate: dayjs('2023-08-25T04:21'),
  attachment: '../fake-data/blob/hipster.png',
  attachmentContentType: 'unknown',
  description: 'paradigm Gorgeous Paradigm',
};

export const sampleWithNewData: NewAssignTask = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

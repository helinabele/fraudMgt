import dayjs from 'dayjs/esm';

import { StatusEnum } from 'app/entities/enumerations/status-enum.model';

import { ITask, NewTask } from './task.model';

export const sampleWithRequiredData: ITask = {
  id: '6cd369ff-3e50-44dc-bff2-85e0c26ef9ed',
  title: "Pa'anga Soft Producer",
};

export const sampleWithPartialData: ITask = {
  id: 'c5135b32-79bc-49af-8bf8-60d0b49af217',
  title: 'black Ergonomic',
  description: 'Table',
  dueDate: dayjs('2023-08-24'),
  attachment: '../fake-data/blob/hipster.png',
  attachmentContentType: 'unknown',
  status: StatusEnum['NEW'],
};

export const sampleWithFullData: ITask = {
  id: '23b64e2d-6d40-41a6-b32d-1dfffd7c633e',
  title: 'synergize Berkshire Usability',
  description: 'orchid',
  dueDate: dayjs('2023-08-24'),
  attachment: '../fake-data/blob/hipster.png',
  attachmentContentType: 'unknown',
  status: StatusEnum['NEW'],
};

export const sampleWithNewData: NewTask = {
  title: 'web',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

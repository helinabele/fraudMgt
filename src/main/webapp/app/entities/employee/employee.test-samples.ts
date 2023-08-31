import dayjs from 'dayjs/esm';

import { Gender } from 'app/entities/enumerations/gender.model';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: '14dd2a5b-fa97-4f64-928f-64d64f5877f3',
  name: 'Pizza paradigm',
  email: 'xtB^~R@P|{.Cs.',
};

export const sampleWithPartialData: IEmployee = {
  id: '9e842aa6-7618-44ec-86c8-aa24601072ee',
  employeeCode: 'Handmade Bhutanese',
  name: 'Tactics',
  genderType: Gender['MALE'],
  email: 'o<51Y@wbw&.yIqaYh',
  employeePicture: '../fake-data/blob/hipster.png',
  employeePictureContentType: 'unknown',
};

export const sampleWithFullData: IEmployee = {
  id: '7f19722e-f954-4be7-b35c-983d857af3cd',
  employeeCode: 'web-enabled pixel demand-driven',
  name: 'salmon',
  genderType: Gender['FEMALE'],
  dateOfBirth: dayjs('2023-08-25T02:29'),
  age: 5900,
  email: "n+d;Z@'.X{<Qp7",
  employeePicture: '../fake-data/blob/hipster.png',
  employeePictureContentType: 'unknown',
};

export const sampleWithNewData: NewEmployee = {
  name: 'Baby Tennessee Tasty',
  email: ':K@}m.AJ',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

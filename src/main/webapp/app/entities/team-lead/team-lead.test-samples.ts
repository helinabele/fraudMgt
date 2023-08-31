import { ITeamLead, NewTeamLead } from './team-lead.model';

export const sampleWithRequiredData: ITeamLead = {
  id: '2ac4e022-9b71-4af0-87b3-fd6c32f3b925',
};

export const sampleWithPartialData: ITeamLead = {
  id: 'ac489acc-5061-4902-bfce-1ee7ed9c9584',
  teamLeadName: 'withdrawal',
  description: 'Synchronised Synchronised',
  managerialId: 92961,
};

export const sampleWithFullData: ITeamLead = {
  id: '96e63be7-03b4-4e5f-a2e4-354167cb7fde',
  teamLeadName: 'firewall Borders',
  description: 'Bedfordshire',
  managerialId: 53535,
};

export const sampleWithNewData: NewTeamLead = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { ITeamLead } from 'app/entities/team-lead/team-lead.model';
import { IManagerial } from 'app/entities/managerial/managerial.model';

export interface ITeam {
  id: string;
  teamName?: string | null;
  description?: string | null;
  managerialId?: number | null;
  isCreator?: boolean | null;
  teamLead?: Pick<ITeamLead, 'id' | 'teamLeadName'> | null;
  managers?: Pick<IManagerial, 'id' | 'managerialName'> | null;
}

export type NewTeam = Omit<ITeam, 'id'> & { id: null };

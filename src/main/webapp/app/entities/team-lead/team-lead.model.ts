import { IUser } from 'app/entities/user/user.model';
import { IManagerial } from 'app/entities/managerial/managerial.model';

export interface ITeamLead {
  id: string;
  teamLeadName?: string | null;
  description?: string | null;
  managerialId?: number | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  managers?: Pick<IManagerial, 'id' | 'managerialName'> | null;
}

export type NewTeamLead = Omit<ITeamLead, 'id'> & { id: null };

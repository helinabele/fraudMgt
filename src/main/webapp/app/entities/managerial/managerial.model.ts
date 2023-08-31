import { IUser } from 'app/entities/user/user.model';
import { IDirector } from 'app/entities/director/director.model';

export interface IManagerial {
  id: string;
  managerialName?: string | null;
  description?: string | null;
  directorId?: number | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  directors?: Pick<IDirector, 'id' | 'directorName'> | null;
}

export type NewManagerial = Omit<IManagerial, 'id'> & { id: null };

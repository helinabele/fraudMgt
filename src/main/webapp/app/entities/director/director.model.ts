import { IUser } from 'app/entities/user/user.model';

export interface IDirector {
  id: string;
  directorName?: string | null;
  description?: string | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewDirector = Omit<IDirector, 'id'> & { id: null };

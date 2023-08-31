export interface IExternalEmployee {
  id: string;
  name?: string | null;
  occupation?: string | null;
  telephone?: string | null;
  address?: string | null;
}

export type NewExternalEmployee = Omit<IExternalEmployee, 'id'> & { id: null };

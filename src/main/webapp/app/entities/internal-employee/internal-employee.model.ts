export interface IInternalEmployee {
  id: string;
  name?: string | null;
  position?: string | null;
  grade?: string | null;
  experience?: string | null;
  branch?: string | null;
}

export type NewInternalEmployee = Omit<IInternalEmployee, 'id'> & { id: null };

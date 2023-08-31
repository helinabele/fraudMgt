export interface IBankAccount {
  id: string;
  bankName?: string | null;
  description?: string | null;
}

export type NewBankAccount = Omit<IBankAccount, 'id'> & { id: null };

export interface IBankService {
  id: string;
  serviceName?: string | null;
  description?: string | null;
}

export type NewBankService = Omit<IBankService, 'id'> & { id: null };

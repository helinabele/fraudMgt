export interface IFraudType {
  id: string;
  fraudName?: string | null;
  description?: string | null;
  attachment?: string | null;
  attachmentContentType?: string | null;
}

export type NewFraudType = Omit<IFraudType, 'id'> & { id: null };

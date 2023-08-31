import { Gender } from 'app/entities/enumerations/gender.model';

export interface IWhistleBlowerReport {
  id: string;
  fullName?: string | null;
  genderType?: Gender | null;
  emailAdress?: string | null;
  phone?: number | null;
  organization?: string | null;
  message?: string | null;
  attachment?: string | null;
  attachmentContentType?: string | null;
}

export type NewWhistleBlowerReport = Omit<IWhistleBlowerReport, 'id'> & { id: null };

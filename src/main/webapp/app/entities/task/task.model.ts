import dayjs from 'dayjs/esm';
import { StatusEnum } from 'app/entities/enumerations/status-enum.model';

export interface ITask {
  id: string;
  title?: string | null;
  description?: string | null;
  dueDate?: dayjs.Dayjs | null;
  attachment?: string | null;
  attachmentContentType?: string | null;
  status?: StatusEnum | null;
}

export type NewTask = Omit<ITask, 'id'> & { id: null };

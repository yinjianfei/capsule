import { IComment } from 'app/shared/model/comment.model';

export interface IExamination {
    id?: number;
    patientId?: number;
    doctorId?: number;
    status?: string;
    useDate?: string;
    capsuleId?: number;
    comments?: IComment[];
}

export class Examination implements IExamination {
    constructor(
        public id?: number,
        public patientId?: number,
        public doctorId?: number,
        public status?: string,
        public useDate?: string,
        public capsuleId?: number,
        public comments?: IComment[]
    ) {}
}

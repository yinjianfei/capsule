import { IComment } from 'app/shared/model/comment.model';

export interface IExaminationReading {
    id?: number;
    examinationId?: number;
    expertId?: number;
    applicantId?: number;
    applicantRole?: string;
    diagnoses?: IComment[];
}

export class ExaminationReading implements IExaminationReading {
    constructor(
        public id?: number,
        public examinationId?: number,
        public expertId?: number,
        public applicantId?: number,
        public applicantRole?: string,
        public diagnoses?: IComment[]
    ) {}
}

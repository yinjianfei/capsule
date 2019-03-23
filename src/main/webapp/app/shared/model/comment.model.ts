import { Moment } from 'moment';

export interface IComment {
    id?: number;
    content?: string;
    commentBy?: number;
    commentAt?: Moment;
    examinationId?: number;
    capsuleImageId?: number;
    examinationReadingId?: number;
}

export class Comment implements IComment {
    constructor(
        public id?: number,
        public content?: string,
        public commentBy?: number,
        public commentAt?: Moment,
        public examinationId?: number,
        public capsuleImageId?: number,
        public examinationReadingId?: number
    ) {}
}

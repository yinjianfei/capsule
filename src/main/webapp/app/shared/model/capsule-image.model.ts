import { IComment } from 'app/shared/model/comment.model';

export interface ICapsuleImage {
    id?: number;
    imageUrl?: string;
    comments?: IComment[];
    capsuleId?: number;
}

export class CapsuleImage implements ICapsuleImage {
    constructor(public id?: number, public imageUrl?: string, public comments?: IComment[], public capsuleId?: number) {}
}

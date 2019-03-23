import { ICapsuleImage } from 'app/shared/model/capsule-image.model';

export interface ICapsule {
    id?: number;
    capsuleType?: string;
    capsuleNumber?: string;
    images?: ICapsuleImage[];
    examinationId?: number;
    patientId?: number;
}

export class Capsule implements ICapsule {
    constructor(
        public id?: number,
        public capsuleType?: string,
        public capsuleNumber?: string,
        public images?: ICapsuleImage[],
        public examinationId?: number,
        public patientId?: number
    ) {}
}

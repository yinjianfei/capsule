import { ICapsule } from 'app/shared/model/capsule.model';

export interface IPatient {
    id?: number;
    userId?: number;
    capsules?: ICapsule[];
}

export class Patient implements IPatient {
    constructor(public id?: number, public userId?: number, public capsules?: ICapsule[]) {}
}

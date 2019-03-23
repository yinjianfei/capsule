export interface IDoctor {
    id?: number;
    userId?: number;
}

export class Doctor implements IDoctor {
    constructor(public id?: number, public userId?: number) {}
}

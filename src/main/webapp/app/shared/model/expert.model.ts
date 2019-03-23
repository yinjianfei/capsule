export interface IExpert {
    id?: number;
    userId?: number;
}

export class Expert implements IExpert {
    constructor(public id?: number, public userId?: number) {}
}

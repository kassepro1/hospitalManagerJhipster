import { IServiceho } from 'app/shared/model//serviceho.model';

export interface IDepartement {
    id?: number;
    nom?: string;
    services?: IServiceho[];
}

export class Departement implements IDepartement {
    constructor(public id?: number, public nom?: string, public services?: IServiceho[]) {}
}

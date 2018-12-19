import { IDepartement } from 'app/shared/model//departement.model';
import { IBatiment } from 'app/shared/model//batiment.model';

export interface IServiceho {
    id?: number;
    nom?: string;
    departement?: IDepartement;
    batiments?: IBatiment[];
}

export class Serviceho implements IServiceho {
    constructor(public id?: number, public nom?: string, public departement?: IDepartement, public batiments?: IBatiment[]) {}
}

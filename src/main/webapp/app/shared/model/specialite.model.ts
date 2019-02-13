import { IDocteur } from 'app/shared/model//docteur.model';

export interface ISpecialite {
    id?: number;
    numService?: string;
    libelle?: string;
    specialiteDocs?: IDocteur[];
}

export class Specialite implements ISpecialite {
    constructor(public id?: number, public numService?: string, public libelle?: string, public specialiteDocs?: IDocteur[]) {}
}

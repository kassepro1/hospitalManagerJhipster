import { IType } from 'app/shared/model//type.model';

export interface ICategorie {
    id?: number;
    libelle?: string;
    description?: string;
    etat?: boolean;
    types?: IType[];
}

export class Categorie implements ICategorie {
    constructor(public id?: number, public libelle?: string, public description?: string, public etat?: boolean, public types?: IType[]) {
        this.etat = this.etat || false;
    }
}

import { ITraitement } from 'app/shared/model//traitement.model';

export interface ITypeTraitement {
    id?: number;
    libelle?: string;
    typeTraitements?: ITraitement[];
}

export class TypeTraitement implements ITypeTraitement {
    constructor(public id?: number, public libelle?: string, public typeTraitements?: ITraitement[]) {}
}

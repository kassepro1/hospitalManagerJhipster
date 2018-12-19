import { INiveau } from 'app/shared/model//niveau.model';
import { ITypeChambre } from 'app/shared/model//type-chambre.model';

export interface IChambre {
    id?: number;
    libelle?: string;
    statut?: string;
    surface?: string;
    niveau?: INiveau;
    typeChambre?: ITypeChambre;
}

export class Chambre implements IChambre {
    constructor(
        public id?: number,
        public libelle?: string,
        public statut?: string,
        public surface?: string,
        public niveau?: INiveau,
        public typeChambre?: ITypeChambre
    ) {}
}

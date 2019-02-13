export interface IType {
    id?: number;
    libelle?: string;
    description?: string;
    etat?: boolean;
    categorieId?: number;
    equipementId?: number;
}

export class Type implements IType {
    constructor(
        public id?: number,
        public libelle?: string,
        public description?: string,
        public etat?: boolean,
        public categorieId?: number,
        public equipementId?: number
    ) {
        this.etat = this.etat || false;
    }
}

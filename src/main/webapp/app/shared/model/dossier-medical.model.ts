export interface IDossierMedical {
    id?: number;
    nom?: string;
    prenom?: string;
    numFiche?: string;
    taille?: number;
    poids?: number;
    tension?: string;
    temperature?: string;
    photo?: string;
    resultat?: string;
}

export class DossierMedical implements IDossierMedical {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public numFiche?: string,
        public taille?: number,
        public poids?: number,
        public tension?: string,
        public temperature?: string,
        public photo?: string,
        public resultat?: string
    ) {}
}

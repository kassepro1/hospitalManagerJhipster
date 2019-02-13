import { IRendezVous } from 'app/shared/model//rendez-vous.model';
import { IService } from 'app/shared/model//service.model';
import { ISpecialite } from 'app/shared/model//specialite.model';

export interface IDocteur {
    id?: number;
    code?: string;
    prenom?: string;
    nom?: string;
    tel?: string;
    email?: string;
    docteurRvs?: IRendezVous[];
    service?: IService;
    specialite?: ISpecialite;
}

export class Docteur implements IDocteur {
    constructor(
        public id?: number,
        public code?: string,
        public prenom?: string,
        public nom?: string,
        public tel?: string,
        public email?: string,
        public docteurRvs?: IRendezVous[],
        public service?: IService,
        public specialite?: ISpecialite
    ) {}
}

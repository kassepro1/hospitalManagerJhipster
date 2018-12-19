import { Moment } from 'moment';
import { IRendezVous } from 'app/shared/model//rendez-vous.model';

export interface IPatient {
    id?: number;
    numPatient?: string;
    prenom?: string;
    nom?: string;
    tel?: string;
    adresse?: string;
    email?: string;
    datenaiss?: Moment;
    patientRvs?: IRendezVous[];
}

export class Patient implements IPatient {
    constructor(
        public id?: number,
        public numPatient?: string,
        public prenom?: string,
        public nom?: string,
        public tel?: string,
        public adresse?: string,
        public email?: string,
        public datenaiss?: Moment,
        public patientRvs?: IRendezVous[]
    ) {}
}

import { Moment } from 'moment';
import { IPatient } from 'app/shared/model//patient.model';
import { IDocteur } from 'app/shared/model//docteur.model';

export interface IRendezVous {
    id?: number;
    numRv?: string;
    dateRv?: Moment;
    patient?: IPatient;
    docteur?: IDocteur;
}

export class RendezVous implements IRendezVous {
    constructor(public id?: number, public numRv?: string, public dateRv?: Moment, public patient?: IPatient, public docteur?: IDocteur) {}
}

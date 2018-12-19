import { Moment } from 'moment';
import { IHospitalisation } from 'app/shared/model//hospitalisation.model';
import { ITypeTraitement } from 'app/shared/model//type-traitement.model';

export interface ITraitement {
    id?: number;
    date?: Moment;
    observation?: string;
    resultat?: string;
    detaisTraitement?: string;
    hospitalisation?: IHospitalisation;
    typeTraitement?: ITypeTraitement;
}

export class Traitement implements ITraitement {
    constructor(
        public id?: number,
        public date?: Moment,
        public observation?: string,
        public resultat?: string,
        public detaisTraitement?: string,
        public hospitalisation?: IHospitalisation,
        public typeTraitement?: ITypeTraitement
    ) {}
}

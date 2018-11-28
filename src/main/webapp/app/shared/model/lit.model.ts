import { IHospitalisation } from 'app/shared/model//hospitalisation.model';

export interface ILit {
    id?: number;
    numero?: string;
    etat?: boolean;
    hospitalisation?: IHospitalisation;
}

export class Lit implements ILit {
    constructor(public id?: number, public numero?: string, public etat?: boolean, public hospitalisation?: IHospitalisation) {
        this.etat = this.etat || false;
    }
}

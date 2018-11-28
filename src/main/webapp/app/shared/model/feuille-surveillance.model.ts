import { Moment } from 'moment';
import { IHospitalisation } from 'app/shared/model//hospitalisation.model';

export interface IFeuilleSurveillance {
    id?: number;
    heure?: Moment;
    pouls?: string;
    pa?: string;
    temperature?: string;
    freqRespi?: string;
    diurese?: string;
    globeUterin?: string;
    observation?: string;
    hospitalisation?: IHospitalisation;
}

export class FeuilleSurveillance implements IFeuilleSurveillance {
    constructor(
        public id?: number,
        public heure?: Moment,
        public pouls?: string,
        public pa?: string,
        public temperature?: string,
        public freqRespi?: string,
        public diurese?: string,
        public globeUterin?: string,
        public observation?: string,
        public hospitalisation?: IHospitalisation
    ) {}
}

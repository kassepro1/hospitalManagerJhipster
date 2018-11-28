import { Moment } from 'moment';
import { IFeuilleSurveillance } from 'app/shared/model//feuille-surveillance.model';
import { ILit } from 'app/shared/model//lit.model';
import { ITraitement } from 'app/shared/model//traitement.model';
import { ITypeHospitalisation } from 'app/shared/model//type-hospitalisation.model';

export interface IHospitalisation {
    id?: number;
    dateEntree?: Moment;
    motifEntree?: string;
    dateSortie?: Moment;
    motifSortie?: string;
    dateTransfert?: string;
    dossierMedical?: string;
    feuilleSurveillances?: IFeuilleSurveillance[];
    lits?: ILit[];
    traitements?: ITraitement[];
    typeHospitalisation?: ITypeHospitalisation;
}

export class Hospitalisation implements IHospitalisation {
    constructor(
        public id?: number,
        public dateEntree?: Moment,
        public motifEntree?: string,
        public dateSortie?: Moment,
        public motifSortie?: string,
        public dateTransfert?: string,
        public dossierMedical?: string,
        public feuilleSurveillances?: IFeuilleSurveillance[],
        public lits?: ILit[],
        public traitements?: ITraitement[],
        public typeHospitalisation?: ITypeHospitalisation
    ) {}
}

import { Moment } from 'moment';
import { IChambre } from 'app/shared/model//chambre.model';

export interface ITypeChambre {
    id?: number;
    dateInscription?: Moment;
    chambres?: IChambre[];
}

export class TypeChambre implements ITypeChambre {
    constructor(public id?: number, public dateInscription?: Moment, public chambres?: IChambre[]) {}
}

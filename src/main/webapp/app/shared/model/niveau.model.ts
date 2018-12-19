import { IBatiment } from 'app/shared/model//batiment.model';
import { IChambre } from 'app/shared/model//chambre.model';

export interface INiveau {
    id?: number;
    niveau?: string;
    batiment?: IBatiment;
    chambres?: IChambre[];
}

export class Niveau implements INiveau {
    constructor(public id?: number, public niveau?: string, public batiment?: IBatiment, public chambres?: IChambre[]) {}
}

import { IServiceho } from 'app/shared/model//serviceho.model';
import { INiveau } from 'app/shared/model//niveau.model';

export interface IBatiment {
    id?: number;
    libelle?: string;
    serviceho?: IServiceho;
    niveaus?: INiveau[];
}

export class Batiment implements IBatiment {
    constructor(public id?: number, public libelle?: string, public serviceho?: IServiceho, public niveaus?: INiveau[]) {}
}

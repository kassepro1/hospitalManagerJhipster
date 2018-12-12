import { IDocteur } from 'app/shared/model//docteur.model';

export interface IService {
    id?: number;
    numService?: string;
    libelle?: string;
    serviceDocs?: IDocteur[];
}

export class Service implements IService {
    constructor(public id?: number, public numService?: string, public libelle?: string, public serviceDocs?: IDocteur[]) {}
}

export interface IService {
    id?: number;
    numService?: string;
    libelle?: string;
    equipementId?: number;
}

export class Service implements IService {
    constructor(public id?: number, public numService?: string, public libelle?: string, public equipementId?: number) {}
}

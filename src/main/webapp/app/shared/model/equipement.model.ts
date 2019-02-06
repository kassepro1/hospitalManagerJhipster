import { IService } from 'app/shared/model//service.model';
import { IType } from 'app/shared/model//type.model';

export interface IEquipement {
    id?: number;
    libelle?: string;
    description?: string;
    etat?: string;
    services?: IService[];
    types?: IType[];
}

export class Equipement implements IEquipement {
    constructor(
        public id?: number,
        public libelle?: string,
        public description?: string,
        public etat?: string,
        public services?: IService[],
        public types?: IType[]
    ) {}
}

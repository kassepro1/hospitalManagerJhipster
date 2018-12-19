import { IHospitalisation } from 'app/shared/model//hospitalisation.model';

export interface ITypeHospitalisation {
    id?: number;
    libelle?: string;
    hospitalisations?: IHospitalisation[];
}

export class TypeHospitalisation implements ITypeHospitalisation {
    constructor(public id?: number, public libelle?: string, public hospitalisations?: IHospitalisation[]) {}
}

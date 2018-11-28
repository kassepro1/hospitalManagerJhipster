import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';

@Component({
    selector: 'jhi-type-hospitalisation-detail',
    templateUrl: './type-hospitalisation-detail.component.html'
})
export class TypeHospitalisationDetailComponent implements OnInit {
    typeHospitalisation: ITypeHospitalisation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeHospitalisation }) => {
            this.typeHospitalisation = typeHospitalisation;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHospitalisation } from 'app/shared/model/hospitalisation.model';

@Component({
    selector: 'jhi-hospitalisation-detail',
    templateUrl: './hospitalisation-detail.component.html'
})
export class HospitalisationDetailComponent implements OnInit {
    hospitalisation: IHospitalisation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hospitalisation }) => {
            this.hospitalisation = hospitalisation;
        });
    }

    previousState() {
        window.history.back();
    }
}

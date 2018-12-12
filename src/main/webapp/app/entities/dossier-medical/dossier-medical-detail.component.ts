import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDossierMedical } from 'app/shared/model/dossier-medical.model';

@Component({
    selector: 'jhi-dossier-medical-detail',
    templateUrl: './dossier-medical-detail.component.html'
})
export class DossierMedicalDetailComponent implements OnInit {
    dossierMedical: IDossierMedical;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dossierMedical }) => {
            this.dossierMedical = dossierMedical;
        });
    }

    previousState() {
        window.history.back();
    }
}

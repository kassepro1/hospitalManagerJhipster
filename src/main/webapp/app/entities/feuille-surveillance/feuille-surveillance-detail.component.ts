import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';

@Component({
    selector: 'jhi-feuille-surveillance-detail',
    templateUrl: './feuille-surveillance-detail.component.html'
})
export class FeuilleSurveillanceDetailComponent implements OnInit {
    feuilleSurveillance: IFeuilleSurveillance;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ feuilleSurveillance }) => {
            this.feuilleSurveillance = feuilleSurveillance;
        });
    }

    previousState() {
        window.history.back();
    }
}

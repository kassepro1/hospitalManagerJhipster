import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocteur } from 'app/shared/model/docteur.model';

@Component({
    selector: 'jhi-docteur-detail',
    templateUrl: './docteur-detail.component.html'
})
export class DocteurDetailComponent implements OnInit {
    docteur: IDocteur;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ docteur }) => {
            this.docteur = docteur;
        });
    }

    previousState() {
        window.history.back();
    }
}

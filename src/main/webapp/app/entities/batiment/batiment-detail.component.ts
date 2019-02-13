import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBatiment } from 'app/shared/model/batiment.model';

@Component({
    selector: 'jhi-batiment-detail',
    templateUrl: './batiment-detail.component.html'
})
export class BatimentDetailComponent implements OnInit {
    batiment: IBatiment;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ batiment }) => {
            this.batiment = batiment;
        });
    }

    previousState() {
        window.history.back();
    }
}

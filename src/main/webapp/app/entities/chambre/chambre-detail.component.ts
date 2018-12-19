import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChambre } from 'app/shared/model/chambre.model';

@Component({
    selector: 'jhi-chambre-detail',
    templateUrl: './chambre-detail.component.html'
})
export class ChambreDetailComponent implements OnInit {
    chambre: IChambre;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chambre }) => {
            this.chambre = chambre;
        });
    }

    previousState() {
        window.history.back();
    }
}

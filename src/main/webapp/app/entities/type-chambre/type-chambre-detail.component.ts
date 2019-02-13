import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeChambre } from 'app/shared/model/type-chambre.model';

@Component({
    selector: 'jhi-type-chambre-detail',
    templateUrl: './type-chambre-detail.component.html'
})
export class TypeChambreDetailComponent implements OnInit {
    typeChambre: ITypeChambre;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeChambre }) => {
            this.typeChambre = typeChambre;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeTraitement } from 'app/shared/model/type-traitement.model';

@Component({
    selector: 'jhi-type-traitement-detail',
    templateUrl: './type-traitement-detail.component.html'
})
export class TypeTraitementDetailComponent implements OnInit {
    typeTraitement: ITypeTraitement;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeTraitement }) => {
            this.typeTraitement = typeTraitement;
        });
    }

    previousState() {
        window.history.back();
    }
}

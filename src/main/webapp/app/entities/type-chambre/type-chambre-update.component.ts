import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITypeChambre } from 'app/shared/model/type-chambre.model';
import { TypeChambreService } from './type-chambre.service';

@Component({
    selector: 'jhi-type-chambre-update',
    templateUrl: './type-chambre-update.component.html'
})
export class TypeChambreUpdateComponent implements OnInit {
    typeChambre: ITypeChambre;
    isSaving: boolean;
    dateInscription: string;

    constructor(private typeChambreService: TypeChambreService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeChambre }) => {
            this.typeChambre = typeChambre;
            this.dateInscription =
                this.typeChambre.dateInscription != null ? this.typeChambre.dateInscription.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.typeChambre.dateInscription = this.dateInscription != null ? moment(this.dateInscription, DATE_TIME_FORMAT) : null;
        if (this.typeChambre.id !== undefined) {
            this.subscribeToSaveResponse(this.typeChambreService.update(this.typeChambre));
        } else {
            this.subscribeToSaveResponse(this.typeChambreService.create(this.typeChambre));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITypeChambre>>) {
        result.subscribe((res: HttpResponse<ITypeChambre>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

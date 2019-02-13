import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IChambre } from 'app/shared/model/chambre.model';
import { ChambreService } from './chambre.service';
import { INiveau } from 'app/shared/model/niveau.model';
import { NiveauService } from 'app/entities/niveau';
import { ITypeChambre } from 'app/shared/model/type-chambre.model';
import { TypeChambreService } from 'app/entities/type-chambre';

@Component({
    selector: 'jhi-chambre-update',
    templateUrl: './chambre-update.component.html'
})
export class ChambreUpdateComponent implements OnInit {
    chambre: IChambre;
    isSaving: boolean;

    niveaus: INiveau[];

    typechambres: ITypeChambre[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private chambreService: ChambreService,
        private niveauService: NiveauService,
        private typeChambreService: TypeChambreService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chambre }) => {
            this.chambre = chambre;
        });
        this.niveauService.query().subscribe(
            (res: HttpResponse<INiveau[]>) => {
                this.niveaus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.typeChambreService.query().subscribe(
            (res: HttpResponse<ITypeChambre[]>) => {
                this.typechambres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.chambre.id !== undefined) {
            this.subscribeToSaveResponse(this.chambreService.update(this.chambre));
        } else {
            this.subscribeToSaveResponse(this.chambreService.create(this.chambre));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChambre>>) {
        result.subscribe((res: HttpResponse<IChambre>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackNiveauById(index: number, item: INiveau) {
        return item.id;
    }

    trackTypeChambreById(index: number, item: ITypeChambre) {
        return item.id;
    }
}

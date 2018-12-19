import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { INiveau } from 'app/shared/model/niveau.model';
import { NiveauService } from './niveau.service';
import { IBatiment } from 'app/shared/model/batiment.model';
import { BatimentService } from 'app/entities/batiment';

@Component({
    selector: 'jhi-niveau-update',
    templateUrl: './niveau-update.component.html'
})
export class NiveauUpdateComponent implements OnInit {
    niveau: INiveau;
    isSaving: boolean;

    batiments: IBatiment[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private niveauService: NiveauService,
        private batimentService: BatimentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ niveau }) => {
            this.niveau = niveau;
        });
        this.batimentService.query().subscribe(
            (res: HttpResponse<IBatiment[]>) => {
                this.batiments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.niveau.id !== undefined) {
            this.subscribeToSaveResponse(this.niveauService.update(this.niveau));
        } else {
            this.subscribeToSaveResponse(this.niveauService.create(this.niveau));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<INiveau>>) {
        result.subscribe((res: HttpResponse<INiveau>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBatimentById(index: number, item: IBatiment) {
        return item.id;
    }
}

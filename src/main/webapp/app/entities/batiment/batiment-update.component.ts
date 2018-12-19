import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBatiment } from 'app/shared/model/batiment.model';
import { BatimentService } from './batiment.service';
import { IServiceho } from 'app/shared/model/serviceho.model';
import { ServicehoService } from 'app/entities/serviceho';

@Component({
    selector: 'jhi-batiment-update',
    templateUrl: './batiment-update.component.html'
})
export class BatimentUpdateComponent implements OnInit {
    batiment: IBatiment;
    isSaving: boolean;

    servicehos: IServiceho[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private batimentService: BatimentService,
        private servicehoService: ServicehoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ batiment }) => {
            this.batiment = batiment;
        });
        this.servicehoService.query().subscribe(
            (res: HttpResponse<IServiceho[]>) => {
                this.servicehos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.batiment.id !== undefined) {
            this.subscribeToSaveResponse(this.batimentService.update(this.batiment));
        } else {
            this.subscribeToSaveResponse(this.batimentService.create(this.batiment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBatiment>>) {
        result.subscribe((res: HttpResponse<IBatiment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackServicehoById(index: number, item: IServiceho) {
        return item.id;
    }
}

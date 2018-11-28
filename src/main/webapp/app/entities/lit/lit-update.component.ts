import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILit } from 'app/shared/model/lit.model';
import { LitService } from './lit.service';
import { IHospitalisation } from 'app/shared/model/hospitalisation.model';
import { HospitalisationService } from 'app/entities/hospitalisation';

@Component({
    selector: 'jhi-lit-update',
    templateUrl: './lit-update.component.html'
})
export class LitUpdateComponent implements OnInit {
    lit: ILit;
    isSaving: boolean;

    hospitalisations: IHospitalisation[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private litService: LitService,
        private hospitalisationService: HospitalisationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lit }) => {
            this.lit = lit;
        });
        this.hospitalisationService.query().subscribe(
            (res: HttpResponse<IHospitalisation[]>) => {
                this.hospitalisations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lit.id !== undefined) {
            this.subscribeToSaveResponse(this.litService.update(this.lit));
        } else {
            this.subscribeToSaveResponse(this.litService.create(this.lit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILit>>) {
        result.subscribe((res: HttpResponse<ILit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackHospitalisationById(index: number, item: IHospitalisation) {
        return item.id;
    }
}

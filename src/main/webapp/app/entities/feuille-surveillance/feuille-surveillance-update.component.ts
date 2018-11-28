import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IFeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';
import { FeuilleSurveillanceService } from './feuille-surveillance.service';
import { IHospitalisation } from 'app/shared/model/hospitalisation.model';
import { HospitalisationService } from 'app/entities/hospitalisation';

@Component({
    selector: 'jhi-feuille-surveillance-update',
    templateUrl: './feuille-surveillance-update.component.html'
})
export class FeuilleSurveillanceUpdateComponent implements OnInit {
    feuilleSurveillance: IFeuilleSurveillance;
    isSaving: boolean;

    hospitalisations: IHospitalisation[];
    heureDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private feuilleSurveillanceService: FeuilleSurveillanceService,
        private hospitalisationService: HospitalisationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ feuilleSurveillance }) => {
            this.feuilleSurveillance = feuilleSurveillance;
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
        if (this.feuilleSurveillance.id !== undefined) {
            this.subscribeToSaveResponse(this.feuilleSurveillanceService.update(this.feuilleSurveillance));
        } else {
            this.subscribeToSaveResponse(this.feuilleSurveillanceService.create(this.feuilleSurveillance));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFeuilleSurveillance>>) {
        result.subscribe((res: HttpResponse<IFeuilleSurveillance>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

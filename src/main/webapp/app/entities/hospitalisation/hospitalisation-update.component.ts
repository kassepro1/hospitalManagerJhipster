import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IHospitalisation } from 'app/shared/model/hospitalisation.model';
import { HospitalisationService } from './hospitalisation.service';
import { ITypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';
import { TypeHospitalisationService } from 'app/entities/type-hospitalisation';

@Component({
    selector: 'jhi-hospitalisation-update',
    templateUrl: './hospitalisation-update.component.html'
})
export class HospitalisationUpdateComponent implements OnInit {
    hospitalisation: IHospitalisation;
    isSaving: boolean;

    typehospitalisations: ITypeHospitalisation[];
    dateEntreeDp: any;
    dateSortieDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private hospitalisationService: HospitalisationService,
        private typeHospitalisationService: TypeHospitalisationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hospitalisation }) => {
            this.hospitalisation = hospitalisation;
        });
        this.typeHospitalisationService.query().subscribe(
            (res: HttpResponse<ITypeHospitalisation[]>) => {
                this.typehospitalisations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hospitalisation.id !== undefined) {
            this.subscribeToSaveResponse(this.hospitalisationService.update(this.hospitalisation));
        } else {
            this.subscribeToSaveResponse(this.hospitalisationService.create(this.hospitalisation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHospitalisation>>) {
        result.subscribe((res: HttpResponse<IHospitalisation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTypeHospitalisationById(index: number, item: ITypeHospitalisation) {
        return item.id;
    }
}

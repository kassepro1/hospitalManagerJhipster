import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ITraitement } from 'app/shared/model/traitement.model';
import { TraitementService } from './traitement.service';
import { IHospitalisation } from 'app/shared/model/hospitalisation.model';
import { HospitalisationService } from 'app/entities/hospitalisation';
import { ITypeTraitement } from 'app/shared/model/type-traitement.model';
import { TypeTraitementService } from 'app/entities/type-traitement';

@Component({
    selector: 'jhi-traitement-update',
    templateUrl: './traitement-update.component.html'
})
export class TraitementUpdateComponent implements OnInit {
    traitement: ITraitement;
    isSaving: boolean;

    hospitalisations: IHospitalisation[];

    typetraitements: ITypeTraitement[];
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private traitementService: TraitementService,
        private hospitalisationService: HospitalisationService,
        private typeTraitementService: TypeTraitementService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ traitement }) => {
            this.traitement = traitement;
        });
        this.hospitalisationService.query().subscribe(
            (res: HttpResponse<IHospitalisation[]>) => {
                this.hospitalisations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.typeTraitementService.query().subscribe(
            (res: HttpResponse<ITypeTraitement[]>) => {
                this.typetraitements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.traitement.id !== undefined) {
            this.subscribeToSaveResponse(this.traitementService.update(this.traitement));
        } else {
            this.subscribeToSaveResponse(this.traitementService.create(this.traitement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITraitement>>) {
        result.subscribe((res: HttpResponse<ITraitement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTypeTraitementById(index: number, item: ITypeTraitement) {
        return item.id;
    }
}

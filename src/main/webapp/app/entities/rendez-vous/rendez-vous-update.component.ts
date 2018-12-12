import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IRendezVous } from 'app/shared/model/rendez-vous.model';
import { RendezVousService } from './rendez-vous.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';
import { IDocteur } from 'app/shared/model/docteur.model';
import { DocteurService } from 'app/entities/docteur';

@Component({
    selector: 'jhi-rendez-vous-update',
    templateUrl: './rendez-vous-update.component.html'
})
export class RendezVousUpdateComponent implements OnInit {
    rendezVous: IRendezVous;
    isSaving: boolean;

    patients: IPatient[];

    docteurs: IDocteur[];
    dateRvDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private rendezVousService: RendezVousService,
        private patientService: PatientService,
        private docteurService: DocteurService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rendezVous }) => {
            this.rendezVous = rendezVous;
        });
        this.patientService.query().subscribe(
            (res: HttpResponse<IPatient[]>) => {
                this.patients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.docteurService.query().subscribe(
            (res: HttpResponse<IDocteur[]>) => {
                this.docteurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rendezVous.id !== undefined) {
            this.subscribeToSaveResponse(this.rendezVousService.update(this.rendezVous));
        } else {
            this.subscribeToSaveResponse(this.rendezVousService.create(this.rendezVous));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRendezVous>>) {
        result.subscribe((res: HttpResponse<IRendezVous>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPatientById(index: number, item: IPatient) {
        return item.id;
    }

    trackDocteurById(index: number, item: IDocteur) {
        return item.id;
    }
}

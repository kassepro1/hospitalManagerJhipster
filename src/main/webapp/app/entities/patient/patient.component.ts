import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPatient } from 'app/shared/model/patient.model';
import { Principal } from 'app/core';
import { PatientService } from './patient.service';

@Component({
    selector: 'jhi-patient',
    templateUrl: './patient.component.html'
})
export class PatientComponent implements OnInit, OnDestroy {
    patients: IPatient[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private patientService: PatientService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.patientService.query().subscribe(
            (res: HttpResponse<IPatient[]>) => {
                this.patients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPatients();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPatient) {
        return item.id;
    }

    registerChangeInPatients() {
        this.eventSubscriber = this.eventManager.subscribe('patientListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

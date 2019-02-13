import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDossierMedical } from 'app/shared/model/dossier-medical.model';
import { Principal } from 'app/core';
import { DossierMedicalService } from './dossier-medical.service';

@Component({
    selector: 'jhi-dossier-medical',
    templateUrl: './dossier-medical.component.html'
})
export class DossierMedicalComponent implements OnInit, OnDestroy {
    dossierMedicals: IDossierMedical[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dossierMedicalService: DossierMedicalService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.dossierMedicalService.query().subscribe(
            (res: HttpResponse<IDossierMedical[]>) => {
                this.dossierMedicals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDossierMedicals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDossierMedical) {
        return item.id;
    }

    registerChangeInDossierMedicals() {
        this.eventSubscriber = this.eventManager.subscribe('dossierMedicalListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

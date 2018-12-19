import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDocteur } from 'app/shared/model/docteur.model';
import { Principal } from 'app/core';
import { DocteurService } from './docteur.service';

@Component({
    selector: 'jhi-docteur',
    templateUrl: './docteur.component.html'
})
export class DocteurComponent implements OnInit, OnDestroy {
    docteurs: IDocteur[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private docteurService: DocteurService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.docteurService.query().subscribe(
            (res: HttpResponse<IDocteur[]>) => {
                this.docteurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDocteurs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDocteur) {
        return item.id;
    }

    registerChangeInDocteurs() {
        this.eventSubscriber = this.eventManager.subscribe('docteurListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

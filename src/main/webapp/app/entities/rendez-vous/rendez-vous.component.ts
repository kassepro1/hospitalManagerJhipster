import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRendezVous } from 'app/shared/model/rendez-vous.model';
import { Principal } from 'app/core';
import { RendezVousService } from './rendez-vous.service';

@Component({
    selector: 'jhi-rendez-vous',
    templateUrl: './rendez-vous.component.html'
})
export class RendezVousComponent implements OnInit, OnDestroy {
    rendezVous: IRendezVous[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rendezVousService: RendezVousService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rendezVousService.query().subscribe(
            (res: HttpResponse<IRendezVous[]>) => {
                this.rendezVous = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRendezVous();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRendezVous) {
        return item.id;
    }

    registerChangeInRendezVous() {
        this.eventSubscriber = this.eventManager.subscribe('rendezVousListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

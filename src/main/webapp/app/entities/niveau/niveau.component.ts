import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INiveau } from 'app/shared/model/niveau.model';
import { Principal } from 'app/core';
import { NiveauService } from './niveau.service';

@Component({
    selector: 'jhi-niveau',
    templateUrl: './niveau.component.html'
})
export class NiveauComponent implements OnInit, OnDestroy {
    niveaus: INiveau[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private niveauService: NiveauService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.niveauService.query().subscribe(
            (res: HttpResponse<INiveau[]>) => {
                this.niveaus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInNiveaus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: INiveau) {
        return item.id;
    }

    registerChangeInNiveaus() {
        this.eventSubscriber = this.eventManager.subscribe('niveauListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

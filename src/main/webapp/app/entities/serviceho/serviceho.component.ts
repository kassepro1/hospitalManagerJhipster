import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IServiceho } from 'app/shared/model/serviceho.model';
import { Principal } from 'app/core';
import { ServicehoService } from './serviceho.service';

@Component({
    selector: 'jhi-serviceho',
    templateUrl: './serviceho.component.html'
})
export class ServicehoComponent implements OnInit, OnDestroy {
    servicehos: IServiceho[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private servicehoService: ServicehoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.servicehoService.query().subscribe(
            (res: HttpResponse<IServiceho[]>) => {
                this.servicehos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInServicehos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IServiceho) {
        return item.id;
    }

    registerChangeInServicehos() {
        this.eventSubscriber = this.eventManager.subscribe('servicehoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

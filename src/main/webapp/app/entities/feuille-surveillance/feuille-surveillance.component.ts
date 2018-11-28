import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';
import { Principal } from 'app/core';
import { FeuilleSurveillanceService } from './feuille-surveillance.service';

@Component({
    selector: 'jhi-feuille-surveillance',
    templateUrl: './feuille-surveillance.component.html'
})
export class FeuilleSurveillanceComponent implements OnInit, OnDestroy {
    feuilleSurveillances: IFeuilleSurveillance[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private feuilleSurveillanceService: FeuilleSurveillanceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.feuilleSurveillanceService.query().subscribe(
            (res: HttpResponse<IFeuilleSurveillance[]>) => {
                this.feuilleSurveillances = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFeuilleSurveillances();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFeuilleSurveillance) {
        return item.id;
    }

    registerChangeInFeuilleSurveillances() {
        this.eventSubscriber = this.eventManager.subscribe('feuilleSurveillanceListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

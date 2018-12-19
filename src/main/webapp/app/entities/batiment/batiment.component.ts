import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBatiment } from 'app/shared/model/batiment.model';
import { Principal } from 'app/core';
import { BatimentService } from './batiment.service';

@Component({
    selector: 'jhi-batiment',
    templateUrl: './batiment.component.html'
})
export class BatimentComponent implements OnInit, OnDestroy {
    batiments: IBatiment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private batimentService: BatimentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.batimentService.query().subscribe(
            (res: HttpResponse<IBatiment[]>) => {
                this.batiments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBatiments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBatiment) {
        return item.id;
    }

    registerChangeInBatiments() {
        this.eventSubscriber = this.eventManager.subscribe('batimentListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISpecialite } from 'app/shared/model/specialite.model';
import { Principal } from 'app/core';
import { SpecialiteService } from './specialite.service';

@Component({
    selector: 'jhi-specialite',
    templateUrl: './specialite.component.html'
})
export class SpecialiteComponent implements OnInit, OnDestroy {
    specialites: ISpecialite[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private specialiteService: SpecialiteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.specialiteService.query().subscribe(
            (res: HttpResponse<ISpecialite[]>) => {
                this.specialites = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSpecialites();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISpecialite) {
        return item.id;
    }

    registerChangeInSpecialites() {
        this.eventSubscriber = this.eventManager.subscribe('specialiteListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

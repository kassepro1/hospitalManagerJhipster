import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDepartement } from 'app/shared/model/departement.model';
import { Principal } from 'app/core';
import { DepartementService } from './departement.service';

@Component({
    selector: 'jhi-departement',
    templateUrl: './departement.component.html'
})
export class DepartementComponent implements OnInit, OnDestroy {
    departements: IDepartement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private departementService: DepartementService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.departementService.query().subscribe(
            (res: HttpResponse<IDepartement[]>) => {
                this.departements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDepartements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDepartement) {
        return item.id;
    }

    registerChangeInDepartements() {
        this.eventSubscriber = this.eventManager.subscribe('departementListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

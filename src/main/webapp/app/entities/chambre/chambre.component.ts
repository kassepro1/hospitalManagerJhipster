import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IChambre } from 'app/shared/model/chambre.model';
import { Principal } from 'app/core';
import { ChambreService } from './chambre.service';

@Component({
    selector: 'jhi-chambre',
    templateUrl: './chambre.component.html'
})
export class ChambreComponent implements OnInit, OnDestroy {
    chambres: IChambre[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private chambreService: ChambreService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.chambreService.query().subscribe(
            (res: HttpResponse<IChambre[]>) => {
                this.chambres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInChambres();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IChambre) {
        return item.id;
    }

    registerChangeInChambres() {
        this.eventSubscriber = this.eventManager.subscribe('chambreListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

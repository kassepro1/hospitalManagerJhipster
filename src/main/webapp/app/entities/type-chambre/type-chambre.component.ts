import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITypeChambre } from 'app/shared/model/type-chambre.model';
import { Principal } from 'app/core';
import { TypeChambreService } from './type-chambre.service';

@Component({
    selector: 'jhi-type-chambre',
    templateUrl: './type-chambre.component.html'
})
export class TypeChambreComponent implements OnInit, OnDestroy {
    typeChambres: ITypeChambre[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private typeChambreService: TypeChambreService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.typeChambreService.query().subscribe(
            (res: HttpResponse<ITypeChambre[]>) => {
                this.typeChambres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTypeChambres();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITypeChambre) {
        return item.id;
    }

    registerChangeInTypeChambres() {
        this.eventSubscriber = this.eventManager.subscribe('typeChambreListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';
import { Principal } from 'app/core';
import { TypeHospitalisationService } from './type-hospitalisation.service';

@Component({
    selector: 'jhi-type-hospitalisation',
    templateUrl: './type-hospitalisation.component.html'
})
export class TypeHospitalisationComponent implements OnInit, OnDestroy {
    typeHospitalisations: ITypeHospitalisation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private typeHospitalisationService: TypeHospitalisationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.typeHospitalisationService.query().subscribe(
            (res: HttpResponse<ITypeHospitalisation[]>) => {
                this.typeHospitalisations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTypeHospitalisations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITypeHospitalisation) {
        return item.id;
    }

    registerChangeInTypeHospitalisations() {
        this.eventSubscriber = this.eventManager.subscribe('typeHospitalisationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

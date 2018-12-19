import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IServiceho } from 'app/shared/model/serviceho.model';
import { ServicehoService } from './serviceho.service';
import { IDepartement } from 'app/shared/model/departement.model';
import { DepartementService } from 'app/entities/departement';

@Component({
    selector: 'jhi-serviceho-update',
    templateUrl: './serviceho-update.component.html'
})
export class ServicehoUpdateComponent implements OnInit {
    serviceho: IServiceho;
    isSaving: boolean;

    departements: IDepartement[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private servicehoService: ServicehoService,
        private departementService: DepartementService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ serviceho }) => {
            this.serviceho = serviceho;
        });
        this.departementService.query().subscribe(
            (res: HttpResponse<IDepartement[]>) => {
                this.departements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.serviceho.id !== undefined) {
            this.subscribeToSaveResponse(this.servicehoService.update(this.serviceho));
        } else {
            this.subscribeToSaveResponse(this.servicehoService.create(this.serviceho));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IServiceho>>) {
        result.subscribe((res: HttpResponse<IServiceho>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDepartementById(index: number, item: IDepartement) {
        return item.id;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDocteur } from 'app/shared/model/docteur.model';
import { DocteurService } from './docteur.service';
import { IService } from 'app/shared/model/service.model';
import { ServiceService } from 'app/entities/service';
import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from 'app/entities/specialite';

@Component({
    selector: 'jhi-docteur-update',
    templateUrl: './docteur-update.component.html'
})
export class DocteurUpdateComponent implements OnInit {
    docteur: IDocteur;
    isSaving: boolean;

    services: IService[];

    specialites: ISpecialite[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private docteurService: DocteurService,
        private serviceService: ServiceService,
        private specialiteService: SpecialiteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ docteur }) => {
            this.docteur = docteur;
        });
        this.serviceService.query().subscribe(
            (res: HttpResponse<IService[]>) => {
                this.services = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.specialiteService.query().subscribe(
            (res: HttpResponse<ISpecialite[]>) => {
                this.specialites = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.docteur.id !== undefined) {
            this.subscribeToSaveResponse(this.docteurService.update(this.docteur));
        } else {
            this.subscribeToSaveResponse(this.docteurService.create(this.docteur));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDocteur>>) {
        result.subscribe((res: HttpResponse<IDocteur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackServiceById(index: number, item: IService) {
        return item.id;
    }

    trackSpecialiteById(index: number, item: ISpecialite) {
        return item.id;
    }
}

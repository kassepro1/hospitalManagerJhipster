import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDossierMedical } from 'app/shared/model/dossier-medical.model';
import { DossierMedicalService } from './dossier-medical.service';

@Component({
    selector: 'jhi-dossier-medical-update',
    templateUrl: './dossier-medical-update.component.html'
})
export class DossierMedicalUpdateComponent implements OnInit {
    dossierMedical: IDossierMedical;
    isSaving: boolean;

    constructor(private dossierMedicalService: DossierMedicalService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dossierMedical }) => {
            this.dossierMedical = dossierMedical;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dossierMedical.id !== undefined) {
            this.subscribeToSaveResponse(this.dossierMedicalService.update(this.dossierMedical));
        } else {
            this.subscribeToSaveResponse(this.dossierMedicalService.create(this.dossierMedical));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDossierMedical>>) {
        result.subscribe((res: HttpResponse<IDossierMedical>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

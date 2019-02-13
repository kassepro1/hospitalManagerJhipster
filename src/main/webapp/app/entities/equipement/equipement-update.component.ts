import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEquipement } from 'app/shared/model/equipement.model';
import { EquipementService } from './equipement.service';

@Component({
    selector: 'jhi-equipement-update',
    templateUrl: './equipement-update.component.html'
})
export class EquipementUpdateComponent implements OnInit {
    equipement: IEquipement;
    isSaving: boolean;

    constructor(private equipementService: EquipementService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ equipement }) => {
            this.equipement = equipement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.equipement.id !== undefined) {
            this.subscribeToSaveResponse(this.equipementService.update(this.equipement));
        } else {
            this.subscribeToSaveResponse(this.equipementService.create(this.equipement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEquipement>>) {
        result.subscribe((res: HttpResponse<IEquipement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

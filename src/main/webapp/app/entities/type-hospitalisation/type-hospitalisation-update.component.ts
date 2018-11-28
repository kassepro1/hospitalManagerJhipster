import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';
import { TypeHospitalisationService } from './type-hospitalisation.service';

@Component({
    selector: 'jhi-type-hospitalisation-update',
    templateUrl: './type-hospitalisation-update.component.html'
})
export class TypeHospitalisationUpdateComponent implements OnInit {
    typeHospitalisation: ITypeHospitalisation;
    isSaving: boolean;

    constructor(private typeHospitalisationService: TypeHospitalisationService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeHospitalisation }) => {
            this.typeHospitalisation = typeHospitalisation;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeHospitalisation.id !== undefined) {
            this.subscribeToSaveResponse(this.typeHospitalisationService.update(this.typeHospitalisation));
        } else {
            this.subscribeToSaveResponse(this.typeHospitalisationService.create(this.typeHospitalisation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITypeHospitalisation>>) {
        result.subscribe((res: HttpResponse<ITypeHospitalisation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

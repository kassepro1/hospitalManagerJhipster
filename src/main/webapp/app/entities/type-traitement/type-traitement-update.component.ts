import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITypeTraitement } from 'app/shared/model/type-traitement.model';
import { TypeTraitementService } from './type-traitement.service';

@Component({
    selector: 'jhi-type-traitement-update',
    templateUrl: './type-traitement-update.component.html'
})
export class TypeTraitementUpdateComponent implements OnInit {
    typeTraitement: ITypeTraitement;
    isSaving: boolean;

    constructor(private typeTraitementService: TypeTraitementService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeTraitement }) => {
            this.typeTraitement = typeTraitement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeTraitement.id !== undefined) {
            this.subscribeToSaveResponse(this.typeTraitementService.update(this.typeTraitement));
        } else {
            this.subscribeToSaveResponse(this.typeTraitementService.create(this.typeTraitement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITypeTraitement>>) {
        result.subscribe((res: HttpResponse<ITypeTraitement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

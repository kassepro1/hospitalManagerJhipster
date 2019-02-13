import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IType } from 'app/shared/model/type.model';
import { TypeService } from './type.service';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie';
import { IEquipement } from 'app/shared/model/equipement.model';
import { EquipementService } from 'app/entities/equipement';

@Component({
    selector: 'jhi-type-update',
    templateUrl: './type-update.component.html'
})
export class TypeUpdateComponent implements OnInit {
    type: IType;
    isSaving: boolean;

    categories: ICategorie[];

    equipements: IEquipement[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private typeService: TypeService,
        private categorieService: CategorieService,
        private equipementService: EquipementService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ type }) => {
            this.type = type;
        });
        this.categorieService.query().subscribe(
            (res: HttpResponse<ICategorie[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.equipementService.query().subscribe(
            (res: HttpResponse<IEquipement[]>) => {
                this.equipements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.type.id !== undefined) {
            this.subscribeToSaveResponse(this.typeService.update(this.type));
        } else {
            this.subscribeToSaveResponse(this.typeService.create(this.type));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IType>>) {
        result.subscribe((res: HttpResponse<IType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategorieById(index: number, item: ICategorie) {
        return item.id;
    }

    trackEquipementById(index: number, item: IEquipement) {
        return item.id;
    }
}

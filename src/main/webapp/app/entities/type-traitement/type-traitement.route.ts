import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeTraitement } from 'app/shared/model/type-traitement.model';
import { TypeTraitementService } from './type-traitement.service';
import { TypeTraitementComponent } from './type-traitement.component';
import { TypeTraitementDetailComponent } from './type-traitement-detail.component';
import { TypeTraitementUpdateComponent } from './type-traitement-update.component';
import { TypeTraitementDeletePopupComponent } from './type-traitement-delete-dialog.component';
import { ITypeTraitement } from 'app/shared/model/type-traitement.model';

@Injectable({ providedIn: 'root' })
export class TypeTraitementResolve implements Resolve<ITypeTraitement> {
    constructor(private service: TypeTraitementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TypeTraitement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeTraitement>) => response.ok),
                map((typeTraitement: HttpResponse<TypeTraitement>) => typeTraitement.body)
            );
        }
        return of(new TypeTraitement());
    }
}

export const typeTraitementRoute: Routes = [
    {
        path: 'type-traitement',
        component: TypeTraitementComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'hospitalmanagerApp.typeTraitement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-traitement/:id/view',
        component: TypeTraitementDetailComponent,
        resolve: {
            typeTraitement: TypeTraitementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeTraitement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-traitement/new',
        component: TypeTraitementUpdateComponent,
        resolve: {
            typeTraitement: TypeTraitementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeTraitement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-traitement/:id/edit',
        component: TypeTraitementUpdateComponent,
        resolve: {
            typeTraitement: TypeTraitementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeTraitement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeTraitementPopupRoute: Routes = [
    {
        path: 'type-traitement/:id/delete',
        component: TypeTraitementDeletePopupComponent,
        resolve: {
            typeTraitement: TypeTraitementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeTraitement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

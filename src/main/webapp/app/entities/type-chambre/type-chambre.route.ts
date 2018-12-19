import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeChambre } from 'app/shared/model/type-chambre.model';
import { TypeChambreService } from './type-chambre.service';
import { TypeChambreComponent } from './type-chambre.component';
import { TypeChambreDetailComponent } from './type-chambre-detail.component';
import { TypeChambreUpdateComponent } from './type-chambre-update.component';
import { TypeChambreDeletePopupComponent } from './type-chambre-delete-dialog.component';
import { ITypeChambre } from 'app/shared/model/type-chambre.model';

@Injectable({ providedIn: 'root' })
export class TypeChambreResolve implements Resolve<ITypeChambre> {
    constructor(private service: TypeChambreService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TypeChambre> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeChambre>) => response.ok),
                map((typeChambre: HttpResponse<TypeChambre>) => typeChambre.body)
            );
        }
        return of(new TypeChambre());
    }
}

export const typeChambreRoute: Routes = [
    {
        path: 'type-chambre',
        component: TypeChambreComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeChambre.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-chambre/:id/view',
        component: TypeChambreDetailComponent,
        resolve: {
            typeChambre: TypeChambreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeChambre.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-chambre/new',
        component: TypeChambreUpdateComponent,
        resolve: {
            typeChambre: TypeChambreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeChambre.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-chambre/:id/edit',
        component: TypeChambreUpdateComponent,
        resolve: {
            typeChambre: TypeChambreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeChambre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeChambrePopupRoute: Routes = [
    {
        path: 'type-chambre/:id/delete',
        component: TypeChambreDeletePopupComponent,
        resolve: {
            typeChambre: TypeChambreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeChambre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

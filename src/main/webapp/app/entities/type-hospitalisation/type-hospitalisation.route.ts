import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';
import { TypeHospitalisationService } from './type-hospitalisation.service';
import { TypeHospitalisationComponent } from './type-hospitalisation.component';
import { TypeHospitalisationDetailComponent } from './type-hospitalisation-detail.component';
import { TypeHospitalisationUpdateComponent } from './type-hospitalisation-update.component';
import { TypeHospitalisationDeletePopupComponent } from './type-hospitalisation-delete-dialog.component';
import { ITypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';

@Injectable({ providedIn: 'root' })
export class TypeHospitalisationResolve implements Resolve<ITypeHospitalisation> {
    constructor(private service: TypeHospitalisationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TypeHospitalisation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeHospitalisation>) => response.ok),
                map((typeHospitalisation: HttpResponse<TypeHospitalisation>) => typeHospitalisation.body)
            );
        }
        return of(new TypeHospitalisation());
    }
}

export const typeHospitalisationRoute: Routes = [
    {
        path: 'type-hospitalisation',
        component: TypeHospitalisationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeHospitalisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-hospitalisation/:id/view',
        component: TypeHospitalisationDetailComponent,
        resolve: {
            typeHospitalisation: TypeHospitalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeHospitalisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-hospitalisation/new',
        component: TypeHospitalisationUpdateComponent,
        resolve: {
            typeHospitalisation: TypeHospitalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeHospitalisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-hospitalisation/:id/edit',
        component: TypeHospitalisationUpdateComponent,
        resolve: {
            typeHospitalisation: TypeHospitalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeHospitalisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeHospitalisationPopupRoute: Routes = [
    {
        path: 'type-hospitalisation/:id/delete',
        component: TypeHospitalisationDeletePopupComponent,
        resolve: {
            typeHospitalisation: TypeHospitalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.typeHospitalisation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

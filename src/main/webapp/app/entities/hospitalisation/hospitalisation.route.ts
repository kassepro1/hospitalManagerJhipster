import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Hospitalisation } from 'app/shared/model/hospitalisation.model';
import { HospitalisationService } from './hospitalisation.service';
import { HospitalisationComponent } from './hospitalisation.component';
import { HospitalisationDetailComponent } from './hospitalisation-detail.component';
import { HospitalisationUpdateComponent } from './hospitalisation-update.component';
import { HospitalisationDeletePopupComponent } from './hospitalisation-delete-dialog.component';
import { IHospitalisation } from 'app/shared/model/hospitalisation.model';

@Injectable({ providedIn: 'root' })
export class HospitalisationResolve implements Resolve<IHospitalisation> {
    constructor(private service: HospitalisationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Hospitalisation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Hospitalisation>) => response.ok),
                map((hospitalisation: HttpResponse<Hospitalisation>) => hospitalisation.body)
            );
        }
        return of(new Hospitalisation());
    }
}

export const hospitalisationRoute: Routes = [
    {
        path: 'hospitalisation',
        component: HospitalisationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.hospitalisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hospitalisation/:id/view',
        component: HospitalisationDetailComponent,
        resolve: {
            hospitalisation: HospitalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.hospitalisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hospitalisation/new',
        component: HospitalisationUpdateComponent,
        resolve: {
            hospitalisation: HospitalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.hospitalisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hospitalisation/:id/edit',
        component: HospitalisationUpdateComponent,
        resolve: {
            hospitalisation: HospitalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.hospitalisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hospitalisationPopupRoute: Routes = [
    {
        path: 'hospitalisation/:id/delete',
        component: HospitalisationDeletePopupComponent,
        resolve: {
            hospitalisation: HospitalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.hospitalisation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

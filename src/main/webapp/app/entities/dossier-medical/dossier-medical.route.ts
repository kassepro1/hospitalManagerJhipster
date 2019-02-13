import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DossierMedical } from 'app/shared/model/dossier-medical.model';
import { DossierMedicalService } from './dossier-medical.service';
import { DossierMedicalComponent } from './dossier-medical.component';
import { DossierMedicalDetailComponent } from './dossier-medical-detail.component';
import { DossierMedicalUpdateComponent } from './dossier-medical-update.component';
import { DossierMedicalDeletePopupComponent } from './dossier-medical-delete-dialog.component';
import { IDossierMedical } from 'app/shared/model/dossier-medical.model';

@Injectable({ providedIn: 'root' })
export class DossierMedicalResolve implements Resolve<IDossierMedical> {
    constructor(private service: DossierMedicalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DossierMedical> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DossierMedical>) => response.ok),
                map((dossierMedical: HttpResponse<DossierMedical>) => dossierMedical.body)
            );
        }
        return of(new DossierMedical());
    }
}

export const dossierMedicalRoute: Routes = [
    {
        path: 'dossier-medical',
        component: DossierMedicalComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.dossierMedical.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dossier-medical/:id/view',
        component: DossierMedicalDetailComponent,
        resolve: {
            dossierMedical: DossierMedicalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.dossierMedical.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dossier-medical/new',
        component: DossierMedicalUpdateComponent,
        resolve: {
            dossierMedical: DossierMedicalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.dossierMedical.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dossier-medical/:id/edit',
        component: DossierMedicalUpdateComponent,
        resolve: {
            dossierMedical: DossierMedicalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.dossierMedical.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dossierMedicalPopupRoute: Routes = [
    {
        path: 'dossier-medical/:id/delete',
        component: DossierMedicalDeletePopupComponent,
        resolve: {
            dossierMedical: DossierMedicalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.dossierMedical.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

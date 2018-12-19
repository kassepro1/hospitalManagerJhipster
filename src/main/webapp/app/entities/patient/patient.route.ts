import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Patient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';
import { PatientComponent } from './patient.component';
import { PatientDetailComponent } from './patient-detail.component';
import { PatientUpdateComponent } from './patient-update.component';
import { PatientDeletePopupComponent } from './patient-delete-dialog.component';
import { IPatient } from 'app/shared/model/patient.model';

@Injectable({ providedIn: 'root' })
export class PatientResolve implements Resolve<IPatient> {
    constructor(private service: PatientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Patient> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Patient>) => response.ok),
                map((patient: HttpResponse<Patient>) => patient.body)
            );
        }
        return of(new Patient());
    }
}

export const patientRoute: Routes = [
    {
        path: 'patient',
        component: PatientComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.patient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'patient/:id/view',
        component: PatientDetailComponent,
        resolve: {
            patient: PatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.patient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'patient/new',
        component: PatientUpdateComponent,
        resolve: {
            patient: PatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.patient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'patient/:id/edit',
        component: PatientUpdateComponent,
        resolve: {
            patient: PatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.patient.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const patientPopupRoute: Routes = [
    {
        path: 'patient/:id/delete',
        component: PatientDeletePopupComponent,
        resolve: {
            patient: PatientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.patient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

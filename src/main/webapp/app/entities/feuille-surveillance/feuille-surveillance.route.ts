import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';
import { FeuilleSurveillanceService } from './feuille-surveillance.service';
import { FeuilleSurveillanceComponent } from './feuille-surveillance.component';
import { FeuilleSurveillanceDetailComponent } from './feuille-surveillance-detail.component';
import { FeuilleSurveillanceUpdateComponent } from './feuille-surveillance-update.component';
import { FeuilleSurveillanceDeletePopupComponent } from './feuille-surveillance-delete-dialog.component';
import { IFeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';

@Injectable({ providedIn: 'root' })
export class FeuilleSurveillanceResolve implements Resolve<IFeuilleSurveillance> {
    constructor(private service: FeuilleSurveillanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<FeuilleSurveillance> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FeuilleSurveillance>) => response.ok),
                map((feuilleSurveillance: HttpResponse<FeuilleSurveillance>) => feuilleSurveillance.body)
            );
        }
        return of(new FeuilleSurveillance());
    }
}

export const feuilleSurveillanceRoute: Routes = [
    {
        path: 'feuille-surveillance',
        component: FeuilleSurveillanceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.feuilleSurveillance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feuille-surveillance/:id/view',
        component: FeuilleSurveillanceDetailComponent,
        resolve: {
            feuilleSurveillance: FeuilleSurveillanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.feuilleSurveillance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feuille-surveillance/new',
        component: FeuilleSurveillanceUpdateComponent,
        resolve: {
            feuilleSurveillance: FeuilleSurveillanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.feuilleSurveillance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feuille-surveillance/:id/edit',
        component: FeuilleSurveillanceUpdateComponent,
        resolve: {
            feuilleSurveillance: FeuilleSurveillanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.feuilleSurveillance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const feuilleSurveillancePopupRoute: Routes = [
    {
        path: 'feuille-surveillance/:id/delete',
        component: FeuilleSurveillanceDeletePopupComponent,
        resolve: {
            feuilleSurveillance: FeuilleSurveillanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.feuilleSurveillance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

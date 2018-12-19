import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Chambre } from 'app/shared/model/chambre.model';
import { ChambreService } from './chambre.service';
import { ChambreComponent } from './chambre.component';
import { ChambreDetailComponent } from './chambre-detail.component';
import { ChambreUpdateComponent } from './chambre-update.component';
import { ChambreDeletePopupComponent } from './chambre-delete-dialog.component';
import { IChambre } from 'app/shared/model/chambre.model';

@Injectable({ providedIn: 'root' })
export class ChambreResolve implements Resolve<IChambre> {
    constructor(private service: ChambreService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Chambre> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Chambre>) => response.ok),
                map((chambre: HttpResponse<Chambre>) => chambre.body)
            );
        }
        return of(new Chambre());
    }
}

export const chambreRoute: Routes = [
    {
        path: 'chambre',
        component: ChambreComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.chambre.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chambre/:id/view',
        component: ChambreDetailComponent,
        resolve: {
            chambre: ChambreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.chambre.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chambre/new',
        component: ChambreUpdateComponent,
        resolve: {
            chambre: ChambreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.chambre.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chambre/:id/edit',
        component: ChambreUpdateComponent,
        resolve: {
            chambre: ChambreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.chambre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chambrePopupRoute: Routes = [
    {
        path: 'chambre/:id/delete',
        component: ChambreDeletePopupComponent,
        resolve: {
            chambre: ChambreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.chambre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Traitement } from 'app/shared/model/traitement.model';
import { TraitementService } from './traitement.service';
import { TraitementComponent } from './traitement.component';
import { TraitementDetailComponent } from './traitement-detail.component';
import { TraitementUpdateComponent } from './traitement-update.component';
import { TraitementDeletePopupComponent } from './traitement-delete-dialog.component';
import { ITraitement } from 'app/shared/model/traitement.model';

@Injectable({ providedIn: 'root' })
export class TraitementResolve implements Resolve<ITraitement> {
    constructor(private service: TraitementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Traitement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Traitement>) => response.ok),
                map((traitement: HttpResponse<Traitement>) => traitement.body)
            );
        }
        return of(new Traitement());
    }
}

export const traitementRoute: Routes = [
    {
        path: 'traitement',
        component: TraitementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.traitement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'traitement/:id/view',
        component: TraitementDetailComponent,
        resolve: {
            traitement: TraitementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.traitement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'traitement/new',
        component: TraitementUpdateComponent,
        resolve: {
            traitement: TraitementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.traitement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'traitement/:id/edit',
        component: TraitementUpdateComponent,
        resolve: {
            traitement: TraitementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.traitement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const traitementPopupRoute: Routes = [
    {
        path: 'traitement/:id/delete',
        component: TraitementDeletePopupComponent,
        resolve: {
            traitement: TraitementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.traitement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

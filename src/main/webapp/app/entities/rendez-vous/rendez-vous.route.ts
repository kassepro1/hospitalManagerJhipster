import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RendezVous } from 'app/shared/model/rendez-vous.model';
import { RendezVousService } from './rendez-vous.service';
import { RendezVousComponent } from './rendez-vous.component';
import { RendezVousDetailComponent } from './rendez-vous-detail.component';
import { RendezVousUpdateComponent } from './rendez-vous-update.component';
import { RendezVousDeletePopupComponent } from './rendez-vous-delete-dialog.component';
import { IRendezVous } from 'app/shared/model/rendez-vous.model';

@Injectable({ providedIn: 'root' })
export class RendezVousResolve implements Resolve<IRendezVous> {
    constructor(private service: RendezVousService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RendezVous> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RendezVous>) => response.ok),
                map((rendezVous: HttpResponse<RendezVous>) => rendezVous.body)
            );
        }
        return of(new RendezVous());
    }
}

export const rendezVousRoute: Routes = [
    {
        path: 'rendez-vous',
        component: RendezVousComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.rendezVous.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rendez-vous/:id/view',
        component: RendezVousDetailComponent,
        resolve: {
            rendezVous: RendezVousResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.rendezVous.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rendez-vous/new',
        component: RendezVousUpdateComponent,
        resolve: {
            rendezVous: RendezVousResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.rendezVous.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rendez-vous/:id/edit',
        component: RendezVousUpdateComponent,
        resolve: {
            rendezVous: RendezVousResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.rendezVous.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rendezVousPopupRoute: Routes = [
    {
        path: 'rendez-vous/:id/delete',
        component: RendezVousDeletePopupComponent,
        resolve: {
            rendezVous: RendezVousResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.rendezVous.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

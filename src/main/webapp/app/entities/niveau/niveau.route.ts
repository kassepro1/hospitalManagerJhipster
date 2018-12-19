import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Niveau } from 'app/shared/model/niveau.model';
import { NiveauService } from './niveau.service';
import { NiveauComponent } from './niveau.component';
import { NiveauDetailComponent } from './niveau-detail.component';
import { NiveauUpdateComponent } from './niveau-update.component';
import { NiveauDeletePopupComponent } from './niveau-delete-dialog.component';
import { INiveau } from 'app/shared/model/niveau.model';

@Injectable({ providedIn: 'root' })
export class NiveauResolve implements Resolve<INiveau> {
    constructor(private service: NiveauService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Niveau> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Niveau>) => response.ok),
                map((niveau: HttpResponse<Niveau>) => niveau.body)
            );
        }
        return of(new Niveau());
    }
}

export const niveauRoute: Routes = [
    {
        path: 'niveau',
        component: NiveauComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.niveau.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'niveau/:id/view',
        component: NiveauDetailComponent,
        resolve: {
            niveau: NiveauResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.niveau.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'niveau/new',
        component: NiveauUpdateComponent,
        resolve: {
            niveau: NiveauResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.niveau.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'niveau/:id/edit',
        component: NiveauUpdateComponent,
        resolve: {
            niveau: NiveauResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.niveau.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const niveauPopupRoute: Routes = [
    {
        path: 'niveau/:id/delete',
        component: NiveauDeletePopupComponent,
        resolve: {
            niveau: NiveauResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.niveau.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

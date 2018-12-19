import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Docteur } from 'app/shared/model/docteur.model';
import { DocteurService } from './docteur.service';
import { DocteurComponent } from './docteur.component';
import { DocteurDetailComponent } from './docteur-detail.component';
import { DocteurUpdateComponent } from './docteur-update.component';
import { DocteurDeletePopupComponent } from './docteur-delete-dialog.component';
import { IDocteur } from 'app/shared/model/docteur.model';

@Injectable({ providedIn: 'root' })
export class DocteurResolve implements Resolve<IDocteur> {
    constructor(private service: DocteurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Docteur> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Docteur>) => response.ok),
                map((docteur: HttpResponse<Docteur>) => docteur.body)
            );
        }
        return of(new Docteur());
    }
}

export const docteurRoute: Routes = [
    {
        path: 'docteur',
        component: DocteurComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.docteur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'docteur/:id/view',
        component: DocteurDetailComponent,
        resolve: {
            docteur: DocteurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.docteur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'docteur/new',
        component: DocteurUpdateComponent,
        resolve: {
            docteur: DocteurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.docteur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'docteur/:id/edit',
        component: DocteurUpdateComponent,
        resolve: {
            docteur: DocteurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.docteur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const docteurPopupRoute: Routes = [
    {
        path: 'docteur/:id/delete',
        component: DocteurDeletePopupComponent,
        resolve: {
            docteur: DocteurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.docteur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

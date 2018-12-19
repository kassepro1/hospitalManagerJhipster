import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Lit } from 'app/shared/model/lit.model';
import { LitService } from './lit.service';
import { LitComponent } from './lit.component';
import { LitDetailComponent } from './lit-detail.component';
import { LitUpdateComponent } from './lit-update.component';
import { LitDeletePopupComponent } from './lit-delete-dialog.component';
import { ILit } from 'app/shared/model/lit.model';

@Injectable({ providedIn: 'root' })
export class LitResolve implements Resolve<ILit> {
    constructor(private service: LitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Lit> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Lit>) => response.ok),
                map((lit: HttpResponse<Lit>) => lit.body)
            );
        }
        return of(new Lit());
    }
}

export const litRoute: Routes = [
    {
        path: 'lit',
        component: LitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.lit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lit/:id/view',
        component: LitDetailComponent,
        resolve: {
            lit: LitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.lit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lit/new',
        component: LitUpdateComponent,
        resolve: {
            lit: LitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.lit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lit/:id/edit',
        component: LitUpdateComponent,
        resolve: {
            lit: LitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.lit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const litPopupRoute: Routes = [
    {
        path: 'lit/:id/delete',
        component: LitDeletePopupComponent,
        resolve: {
            lit: LitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.lit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

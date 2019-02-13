import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Serviceho } from 'app/shared/model/serviceho.model';
import { ServicehoService } from './serviceho.service';
import { ServicehoComponent } from './serviceho.component';
import { ServicehoDetailComponent } from './serviceho-detail.component';
import { ServicehoUpdateComponent } from './serviceho-update.component';
import { ServicehoDeletePopupComponent } from './serviceho-delete-dialog.component';
import { IServiceho } from 'app/shared/model/serviceho.model';

@Injectable({ providedIn: 'root' })
export class ServicehoResolve implements Resolve<IServiceho> {
    constructor(private service: ServicehoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Serviceho> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Serviceho>) => response.ok),
                map((serviceho: HttpResponse<Serviceho>) => serviceho.body)
            );
        }
        return of(new Serviceho());
    }
}

export const servicehoRoute: Routes = [
    {
        path: 'serviceho',
        component: ServicehoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.serviceho.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'serviceho/:id/view',
        component: ServicehoDetailComponent,
        resolve: {
            serviceho: ServicehoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.serviceho.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'serviceho/new',
        component: ServicehoUpdateComponent,
        resolve: {
            serviceho: ServicehoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.serviceho.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'serviceho/:id/edit',
        component: ServicehoUpdateComponent,
        resolve: {
            serviceho: ServicehoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.serviceho.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const servicehoPopupRoute: Routes = [
    {
        path: 'serviceho/:id/delete',
        component: ServicehoDeletePopupComponent,
        resolve: {
            serviceho: ServicehoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.serviceho.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

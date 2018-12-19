import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Batiment } from 'app/shared/model/batiment.model';
import { BatimentService } from './batiment.service';
import { BatimentComponent } from './batiment.component';
import { BatimentDetailComponent } from './batiment-detail.component';
import { BatimentUpdateComponent } from './batiment-update.component';
import { BatimentDeletePopupComponent } from './batiment-delete-dialog.component';
import { IBatiment } from 'app/shared/model/batiment.model';

@Injectable({ providedIn: 'root' })
export class BatimentResolve implements Resolve<IBatiment> {
    constructor(private service: BatimentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Batiment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Batiment>) => response.ok),
                map((batiment: HttpResponse<Batiment>) => batiment.body)
            );
        }
        return of(new Batiment());
    }
}

export const batimentRoute: Routes = [
    {
        path: 'batiment',
        component: BatimentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.batiment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'batiment/:id/view',
        component: BatimentDetailComponent,
        resolve: {
            batiment: BatimentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.batiment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'batiment/new',
        component: BatimentUpdateComponent,
        resolve: {
            batiment: BatimentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.batiment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'batiment/:id/edit',
        component: BatimentUpdateComponent,
        resolve: {
            batiment: BatimentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.batiment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const batimentPopupRoute: Routes = [
    {
        path: 'batiment/:id/delete',
        component: BatimentDeletePopupComponent,
        resolve: {
            batiment: BatimentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hospitalmanagerApp.batiment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

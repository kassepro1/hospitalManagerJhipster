import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    TypeComponent,
    TypeDetailComponent,
    TypeUpdateComponent,
    TypeDeletePopupComponent,
    TypeDeleteDialogComponent,
    typeRoute,
    typePopupRoute
} from './';

const ENTITY_STATES = [...typeRoute, ...typePopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TypeComponent, TypeDetailComponent, TypeUpdateComponent, TypeDeleteDialogComponent, TypeDeletePopupComponent],
    entryComponents: [TypeComponent, TypeUpdateComponent, TypeDeleteDialogComponent, TypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerTypeModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    SpecialiteComponent,
    SpecialiteDetailComponent,
    SpecialiteUpdateComponent,
    SpecialiteDeletePopupComponent,
    SpecialiteDeleteDialogComponent,
    specialiteRoute,
    specialitePopupRoute
} from './';

const ENTITY_STATES = [...specialiteRoute, ...specialitePopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SpecialiteComponent,
        SpecialiteDetailComponent,
        SpecialiteUpdateComponent,
        SpecialiteDeleteDialogComponent,
        SpecialiteDeletePopupComponent
    ],
    entryComponents: [SpecialiteComponent, SpecialiteUpdateComponent, SpecialiteDeleteDialogComponent, SpecialiteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerSpecialiteModule {}

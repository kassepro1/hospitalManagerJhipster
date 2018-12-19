import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    DocteurComponent,
    DocteurDetailComponent,
    DocteurUpdateComponent,
    DocteurDeletePopupComponent,
    DocteurDeleteDialogComponent,
    docteurRoute,
    docteurPopupRoute
} from './';

const ENTITY_STATES = [...docteurRoute, ...docteurPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DocteurComponent,
        DocteurDetailComponent,
        DocteurUpdateComponent,
        DocteurDeleteDialogComponent,
        DocteurDeletePopupComponent
    ],
    entryComponents: [DocteurComponent, DocteurUpdateComponent, DocteurDeleteDialogComponent, DocteurDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerDocteurModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    ServicehoComponent,
    ServicehoDetailComponent,
    ServicehoUpdateComponent,
    ServicehoDeletePopupComponent,
    ServicehoDeleteDialogComponent,
    servicehoRoute,
    servicehoPopupRoute
} from './';

const ENTITY_STATES = [...servicehoRoute, ...servicehoPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ServicehoComponent,
        ServicehoDetailComponent,
        ServicehoUpdateComponent,
        ServicehoDeleteDialogComponent,
        ServicehoDeletePopupComponent
    ],
    entryComponents: [ServicehoComponent, ServicehoUpdateComponent, ServicehoDeleteDialogComponent, ServicehoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerServicehoModule {}

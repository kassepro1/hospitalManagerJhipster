import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    HospitalisationComponent,
    HospitalisationDetailComponent,
    HospitalisationUpdateComponent,
    HospitalisationDeletePopupComponent,
    HospitalisationDeleteDialogComponent,
    hospitalisationRoute,
    hospitalisationPopupRoute
} from './';

const ENTITY_STATES = [...hospitalisationRoute, ...hospitalisationPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HospitalisationComponent,
        HospitalisationDetailComponent,
        HospitalisationUpdateComponent,
        HospitalisationDeleteDialogComponent,
        HospitalisationDeletePopupComponent
    ],
    entryComponents: [
        HospitalisationComponent,
        HospitalisationUpdateComponent,
        HospitalisationDeleteDialogComponent,
        HospitalisationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerHospitalisationModule {}

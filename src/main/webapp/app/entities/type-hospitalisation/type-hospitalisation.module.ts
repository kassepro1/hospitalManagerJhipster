import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    TypeHospitalisationComponent,
    TypeHospitalisationDetailComponent,
    TypeHospitalisationUpdateComponent,
    TypeHospitalisationDeletePopupComponent,
    TypeHospitalisationDeleteDialogComponent,
    typeHospitalisationRoute,
    typeHospitalisationPopupRoute
} from './';

const ENTITY_STATES = [...typeHospitalisationRoute, ...typeHospitalisationPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeHospitalisationComponent,
        TypeHospitalisationDetailComponent,
        TypeHospitalisationUpdateComponent,
        TypeHospitalisationDeleteDialogComponent,
        TypeHospitalisationDeletePopupComponent
    ],
    entryComponents: [
        TypeHospitalisationComponent,
        TypeHospitalisationUpdateComponent,
        TypeHospitalisationDeleteDialogComponent,
        TypeHospitalisationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerTypeHospitalisationModule {}

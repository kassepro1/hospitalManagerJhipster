import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    TypeTraitementComponent,
    TypeTraitementDetailComponent,
    TypeTraitementUpdateComponent,
    TypeTraitementDeletePopupComponent,
    TypeTraitementDeleteDialogComponent,
    typeTraitementRoute,
    typeTraitementPopupRoute
} from './';

const ENTITY_STATES = [...typeTraitementRoute, ...typeTraitementPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeTraitementComponent,
        TypeTraitementDetailComponent,
        TypeTraitementUpdateComponent,
        TypeTraitementDeleteDialogComponent,
        TypeTraitementDeletePopupComponent
    ],
    entryComponents: [
        TypeTraitementComponent,
        TypeTraitementUpdateComponent,
        TypeTraitementDeleteDialogComponent,
        TypeTraitementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerTypeTraitementModule {}

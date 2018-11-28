import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    TraitementComponent,
    TraitementDetailComponent,
    TraitementUpdateComponent,
    TraitementDeletePopupComponent,
    TraitementDeleteDialogComponent,
    traitementRoute,
    traitementPopupRoute
} from './';

const ENTITY_STATES = [...traitementRoute, ...traitementPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TraitementComponent,
        TraitementDetailComponent,
        TraitementUpdateComponent,
        TraitementDeleteDialogComponent,
        TraitementDeletePopupComponent
    ],
    entryComponents: [TraitementComponent, TraitementUpdateComponent, TraitementDeleteDialogComponent, TraitementDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerTraitementModule {}

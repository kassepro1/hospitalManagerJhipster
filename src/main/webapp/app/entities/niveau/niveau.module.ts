import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    NiveauComponent,
    NiveauDetailComponent,
    NiveauUpdateComponent,
    NiveauDeletePopupComponent,
    NiveauDeleteDialogComponent,
    niveauRoute,
    niveauPopupRoute
} from './';

const ENTITY_STATES = [...niveauRoute, ...niveauPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [NiveauComponent, NiveauDetailComponent, NiveauUpdateComponent, NiveauDeleteDialogComponent, NiveauDeletePopupComponent],
    entryComponents: [NiveauComponent, NiveauUpdateComponent, NiveauDeleteDialogComponent, NiveauDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerNiveauModule {}

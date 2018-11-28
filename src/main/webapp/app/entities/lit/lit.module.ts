import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    LitComponent,
    LitDetailComponent,
    LitUpdateComponent,
    LitDeletePopupComponent,
    LitDeleteDialogComponent,
    litRoute,
    litPopupRoute
} from './';

const ENTITY_STATES = [...litRoute, ...litPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LitComponent, LitDetailComponent, LitUpdateComponent, LitDeleteDialogComponent, LitDeletePopupComponent],
    entryComponents: [LitComponent, LitUpdateComponent, LitDeleteDialogComponent, LitDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerLitModule {}

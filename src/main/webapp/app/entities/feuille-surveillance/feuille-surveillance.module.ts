import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    FeuilleSurveillanceComponent,
    FeuilleSurveillanceDetailComponent,
    FeuilleSurveillanceUpdateComponent,
    FeuilleSurveillanceDeletePopupComponent,
    FeuilleSurveillanceDeleteDialogComponent,
    feuilleSurveillanceRoute,
    feuilleSurveillancePopupRoute
} from './';

const ENTITY_STATES = [...feuilleSurveillanceRoute, ...feuilleSurveillancePopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FeuilleSurveillanceComponent,
        FeuilleSurveillanceDetailComponent,
        FeuilleSurveillanceUpdateComponent,
        FeuilleSurveillanceDeleteDialogComponent,
        FeuilleSurveillanceDeletePopupComponent
    ],
    entryComponents: [
        FeuilleSurveillanceComponent,
        FeuilleSurveillanceUpdateComponent,
        FeuilleSurveillanceDeleteDialogComponent,
        FeuilleSurveillanceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerFeuilleSurveillanceModule {}

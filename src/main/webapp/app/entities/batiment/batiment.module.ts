import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    BatimentComponent,
    BatimentDetailComponent,
    BatimentUpdateComponent,
    BatimentDeletePopupComponent,
    BatimentDeleteDialogComponent,
    batimentRoute,
    batimentPopupRoute
} from './';

const ENTITY_STATES = [...batimentRoute, ...batimentPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BatimentComponent,
        BatimentDetailComponent,
        BatimentUpdateComponent,
        BatimentDeleteDialogComponent,
        BatimentDeletePopupComponent
    ],
    entryComponents: [BatimentComponent, BatimentUpdateComponent, BatimentDeleteDialogComponent, BatimentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerBatimentModule {}

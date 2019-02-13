import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    EquipementComponent,
    EquipementDetailComponent,
    EquipementUpdateComponent,
    EquipementDeletePopupComponent,
    EquipementDeleteDialogComponent,
    equipementRoute,
    equipementPopupRoute
} from './';

const ENTITY_STATES = [...equipementRoute, ...equipementPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EquipementComponent,
        EquipementDetailComponent,
        EquipementUpdateComponent,
        EquipementDeleteDialogComponent,
        EquipementDeletePopupComponent
    ],
    entryComponents: [EquipementComponent, EquipementUpdateComponent, EquipementDeleteDialogComponent, EquipementDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerEquipementModule {}

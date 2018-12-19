import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    ChambreComponent,
    ChambreDetailComponent,
    ChambreUpdateComponent,
    ChambreDeletePopupComponent,
    ChambreDeleteDialogComponent,
    chambreRoute,
    chambrePopupRoute
} from './';

const ENTITY_STATES = [...chambreRoute, ...chambrePopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChambreComponent,
        ChambreDetailComponent,
        ChambreUpdateComponent,
        ChambreDeleteDialogComponent,
        ChambreDeletePopupComponent
    ],
    entryComponents: [ChambreComponent, ChambreUpdateComponent, ChambreDeleteDialogComponent, ChambreDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerChambreModule {}

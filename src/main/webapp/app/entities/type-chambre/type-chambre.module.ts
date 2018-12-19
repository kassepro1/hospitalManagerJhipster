import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    TypeChambreComponent,
    TypeChambreDetailComponent,
    TypeChambreUpdateComponent,
    TypeChambreDeletePopupComponent,
    TypeChambreDeleteDialogComponent,
    typeChambreRoute,
    typeChambrePopupRoute
} from './';

const ENTITY_STATES = [...typeChambreRoute, ...typeChambrePopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeChambreComponent,
        TypeChambreDetailComponent,
        TypeChambreUpdateComponent,
        TypeChambreDeleteDialogComponent,
        TypeChambreDeletePopupComponent
    ],
    entryComponents: [TypeChambreComponent, TypeChambreUpdateComponent, TypeChambreDeleteDialogComponent, TypeChambreDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerTypeChambreModule {}

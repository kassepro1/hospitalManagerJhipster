import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalmanagerSharedModule } from 'app/shared';
import {
    DossierMedicalComponent,
    DossierMedicalDetailComponent,
    DossierMedicalUpdateComponent,
    DossierMedicalDeletePopupComponent,
    DossierMedicalDeleteDialogComponent,
    dossierMedicalRoute,
    dossierMedicalPopupRoute
} from './';

const ENTITY_STATES = [...dossierMedicalRoute, ...dossierMedicalPopupRoute];

@NgModule({
    imports: [HospitalmanagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DossierMedicalComponent,
        DossierMedicalDetailComponent,
        DossierMedicalUpdateComponent,
        DossierMedicalDeleteDialogComponent,
        DossierMedicalDeletePopupComponent
    ],
    entryComponents: [
        DossierMedicalComponent,
        DossierMedicalUpdateComponent,
        DossierMedicalDeleteDialogComponent,
        DossierMedicalDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerDossierMedicalModule {}

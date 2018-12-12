import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HospitalmanagerHospitalisationModule } from './hospitalisation/hospitalisation.module';
import { HospitalmanagerTypeHospitalisationModule } from './type-hospitalisation/type-hospitalisation.module';
import { HospitalmanagerFeuilleSurveillanceModule } from './feuille-surveillance/feuille-surveillance.module';
import { HospitalmanagerTraitementModule } from './traitement/traitement.module';
import { HospitalmanagerTypeTraitementModule } from './type-traitement/type-traitement.module';
import { HospitalmanagerLitModule } from './lit/lit.module';
import { HospitalmanagerPatientModule } from './patient/patient.module';
import { HospitalmanagerDocteurModule } from './docteur/docteur.module';
import { HospitalmanagerServiceModule } from './service/service.module';
import { HospitalmanagerSpecialiteModule } from './specialite/specialite.module';
import { HospitalmanagerRendezVousModule } from './rendez-vous/rendez-vous.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        HospitalmanagerHospitalisationModule,
        HospitalmanagerTypeHospitalisationModule,
        HospitalmanagerFeuilleSurveillanceModule,
        HospitalmanagerTraitementModule,
        HospitalmanagerTypeTraitementModule,
        HospitalmanagerLitModule,
        HospitalmanagerPatientModule,
        HospitalmanagerDocteurModule,
        HospitalmanagerServiceModule,
        HospitalmanagerSpecialiteModule,
        HospitalmanagerRendezVousModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalmanagerEntityModule {}

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HospitalmanagerTestModule } from '../../../test.module';
import { DossierMedicalComponent } from 'app/entities/dossier-medical/dossier-medical.component';
import { DossierMedicalService } from 'app/entities/dossier-medical/dossier-medical.service';
import { DossierMedical } from 'app/shared/model/dossier-medical.model';

describe('Component Tests', () => {
    describe('DossierMedical Management Component', () => {
        let comp: DossierMedicalComponent;
        let fixture: ComponentFixture<DossierMedicalComponent>;
        let service: DossierMedicalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [DossierMedicalComponent],
                providers: []
            })
                .overrideTemplate(DossierMedicalComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DossierMedicalComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DossierMedicalService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DossierMedical(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dossierMedicals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

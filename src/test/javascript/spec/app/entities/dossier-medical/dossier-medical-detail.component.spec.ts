/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { DossierMedicalDetailComponent } from 'app/entities/dossier-medical/dossier-medical-detail.component';
import { DossierMedical } from 'app/shared/model/dossier-medical.model';

describe('Component Tests', () => {
    describe('DossierMedical Management Detail Component', () => {
        let comp: DossierMedicalDetailComponent;
        let fixture: ComponentFixture<DossierMedicalDetailComponent>;
        const route = ({ data: of({ dossierMedical: new DossierMedical(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [DossierMedicalDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DossierMedicalDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DossierMedicalDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dossierMedical).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

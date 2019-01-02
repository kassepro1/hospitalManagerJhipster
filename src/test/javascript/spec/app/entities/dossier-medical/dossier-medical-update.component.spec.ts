/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { DossierMedicalUpdateComponent } from 'app/entities/dossier-medical/dossier-medical-update.component';
import { DossierMedicalService } from 'app/entities/dossier-medical/dossier-medical.service';
import { DossierMedical } from 'app/shared/model/dossier-medical.model';

describe('Component Tests', () => {
    describe('DossierMedical Management Update Component', () => {
        let comp: DossierMedicalUpdateComponent;
        let fixture: ComponentFixture<DossierMedicalUpdateComponent>;
        let service: DossierMedicalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [DossierMedicalUpdateComponent]
            })
                .overrideTemplate(DossierMedicalUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DossierMedicalUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DossierMedicalService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DossierMedical(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dossierMedical = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DossierMedical();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dossierMedical = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

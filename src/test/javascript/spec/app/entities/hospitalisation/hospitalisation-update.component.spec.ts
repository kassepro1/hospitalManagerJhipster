/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { HospitalisationUpdateComponent } from 'app/entities/hospitalisation/hospitalisation-update.component';
import { HospitalisationService } from 'app/entities/hospitalisation/hospitalisation.service';
import { Hospitalisation } from 'app/shared/model/hospitalisation.model';

describe('Component Tests', () => {
    describe('Hospitalisation Management Update Component', () => {
        let comp: HospitalisationUpdateComponent;
        let fixture: ComponentFixture<HospitalisationUpdateComponent>;
        let service: HospitalisationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [HospitalisationUpdateComponent]
            })
                .overrideTemplate(HospitalisationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HospitalisationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HospitalisationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Hospitalisation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hospitalisation = entity;
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
                    const entity = new Hospitalisation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hospitalisation = entity;
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

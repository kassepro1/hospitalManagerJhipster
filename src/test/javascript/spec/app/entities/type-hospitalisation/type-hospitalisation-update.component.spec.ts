/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { TypeHospitalisationUpdateComponent } from 'app/entities/type-hospitalisation/type-hospitalisation-update.component';
import { TypeHospitalisationService } from 'app/entities/type-hospitalisation/type-hospitalisation.service';
import { TypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';

describe('Component Tests', () => {
    describe('TypeHospitalisation Management Update Component', () => {
        let comp: TypeHospitalisationUpdateComponent;
        let fixture: ComponentFixture<TypeHospitalisationUpdateComponent>;
        let service: TypeHospitalisationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [TypeHospitalisationUpdateComponent]
            })
                .overrideTemplate(TypeHospitalisationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeHospitalisationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeHospitalisationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TypeHospitalisation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeHospitalisation = entity;
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
                    const entity = new TypeHospitalisation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeHospitalisation = entity;
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

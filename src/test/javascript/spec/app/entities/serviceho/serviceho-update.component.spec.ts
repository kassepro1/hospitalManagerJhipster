/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { ServicehoUpdateComponent } from 'app/entities/serviceho/serviceho-update.component';
import { ServicehoService } from 'app/entities/serviceho/serviceho.service';
import { Serviceho } from 'app/shared/model/serviceho.model';

describe('Component Tests', () => {
    describe('Serviceho Management Update Component', () => {
        let comp: ServicehoUpdateComponent;
        let fixture: ComponentFixture<ServicehoUpdateComponent>;
        let service: ServicehoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [ServicehoUpdateComponent]
            })
                .overrideTemplate(ServicehoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ServicehoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicehoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Serviceho(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.serviceho = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Serviceho();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.serviceho = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

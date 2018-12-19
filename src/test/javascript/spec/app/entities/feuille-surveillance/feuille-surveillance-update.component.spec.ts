/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { FeuilleSurveillanceUpdateComponent } from 'app/entities/feuille-surveillance/feuille-surveillance-update.component';
import { FeuilleSurveillanceService } from 'app/entities/feuille-surveillance/feuille-surveillance.service';
import { FeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';

describe('Component Tests', () => {
    describe('FeuilleSurveillance Management Update Component', () => {
        let comp: FeuilleSurveillanceUpdateComponent;
        let fixture: ComponentFixture<FeuilleSurveillanceUpdateComponent>;
        let service: FeuilleSurveillanceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [FeuilleSurveillanceUpdateComponent]
            })
                .overrideTemplate(FeuilleSurveillanceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FeuilleSurveillanceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeuilleSurveillanceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new FeuilleSurveillance(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.feuilleSurveillance = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new FeuilleSurveillance();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.feuilleSurveillance = entity;
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

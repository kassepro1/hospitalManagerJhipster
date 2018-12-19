/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { ChambreUpdateComponent } from 'app/entities/chambre/chambre-update.component';
import { ChambreService } from 'app/entities/chambre/chambre.service';
import { Chambre } from 'app/shared/model/chambre.model';

describe('Component Tests', () => {
    describe('Chambre Management Update Component', () => {
        let comp: ChambreUpdateComponent;
        let fixture: ComponentFixture<ChambreUpdateComponent>;
        let service: ChambreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [ChambreUpdateComponent]
            })
                .overrideTemplate(ChambreUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChambreUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChambreService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Chambre(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.chambre = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Chambre();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.chambre = entity;
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

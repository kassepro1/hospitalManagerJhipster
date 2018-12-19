/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { TypeChambreUpdateComponent } from 'app/entities/type-chambre/type-chambre-update.component';
import { TypeChambreService } from 'app/entities/type-chambre/type-chambre.service';
import { TypeChambre } from 'app/shared/model/type-chambre.model';

describe('Component Tests', () => {
    describe('TypeChambre Management Update Component', () => {
        let comp: TypeChambreUpdateComponent;
        let fixture: ComponentFixture<TypeChambreUpdateComponent>;
        let service: TypeChambreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [TypeChambreUpdateComponent]
            })
                .overrideTemplate(TypeChambreUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeChambreUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeChambreService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeChambre(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeChambre = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeChambre();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeChambre = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { TypeTraitementUpdateComponent } from 'app/entities/type-traitement/type-traitement-update.component';
import { TypeTraitementService } from 'app/entities/type-traitement/type-traitement.service';
import { TypeTraitement } from 'app/shared/model/type-traitement.model';

describe('Component Tests', () => {
    describe('TypeTraitement Management Update Component', () => {
        let comp: TypeTraitementUpdateComponent;
        let fixture: ComponentFixture<TypeTraitementUpdateComponent>;
        let service: TypeTraitementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [TypeTraitementUpdateComponent]
            })
                .overrideTemplate(TypeTraitementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeTraitementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeTraitementService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TypeTraitement(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeTraitement = entity;
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
                    const entity = new TypeTraitement();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeTraitement = entity;
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

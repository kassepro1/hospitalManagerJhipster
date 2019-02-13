/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { DocteurUpdateComponent } from 'app/entities/docteur/docteur-update.component';
import { DocteurService } from 'app/entities/docteur/docteur.service';
import { Docteur } from 'app/shared/model/docteur.model';

describe('Component Tests', () => {
    describe('Docteur Management Update Component', () => {
        let comp: DocteurUpdateComponent;
        let fixture: ComponentFixture<DocteurUpdateComponent>;
        let service: DocteurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [DocteurUpdateComponent]
            })
                .overrideTemplate(DocteurUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DocteurUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocteurService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Docteur(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.docteur = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Docteur();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.docteur = entity;
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

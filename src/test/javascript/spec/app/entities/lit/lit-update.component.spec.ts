/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { LitUpdateComponent } from 'app/entities/lit/lit-update.component';
import { LitService } from 'app/entities/lit/lit.service';
import { Lit } from 'app/shared/model/lit.model';

describe('Component Tests', () => {
    describe('Lit Management Update Component', () => {
        let comp: LitUpdateComponent;
        let fixture: ComponentFixture<LitUpdateComponent>;
        let service: LitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [LitUpdateComponent]
            })
                .overrideTemplate(LitUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LitUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LitService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Lit(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lit = entity;
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
                    const entity = new Lit();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lit = entity;
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

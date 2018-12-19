/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { LitDetailComponent } from 'app/entities/lit/lit-detail.component';
import { Lit } from 'app/shared/model/lit.model';

describe('Component Tests', () => {
    describe('Lit Management Detail Component', () => {
        let comp: LitDetailComponent;
        let fixture: ComponentFixture<LitDetailComponent>;
        const route = ({ data: of({ lit: new Lit(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [LitDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LitDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LitDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lit).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

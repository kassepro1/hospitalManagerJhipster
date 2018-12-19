/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { DocteurDetailComponent } from 'app/entities/docteur/docteur-detail.component';
import { Docteur } from 'app/shared/model/docteur.model';

describe('Component Tests', () => {
    describe('Docteur Management Detail Component', () => {
        let comp: DocteurDetailComponent;
        let fixture: ComponentFixture<DocteurDetailComponent>;
        const route = ({ data: of({ docteur: new Docteur(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [DocteurDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DocteurDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocteurDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.docteur).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

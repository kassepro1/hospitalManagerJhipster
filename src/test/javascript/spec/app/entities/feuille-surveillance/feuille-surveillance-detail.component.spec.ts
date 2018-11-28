/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { FeuilleSurveillanceDetailComponent } from 'app/entities/feuille-surveillance/feuille-surveillance-detail.component';
import { FeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';

describe('Component Tests', () => {
    describe('FeuilleSurveillance Management Detail Component', () => {
        let comp: FeuilleSurveillanceDetailComponent;
        let fixture: ComponentFixture<FeuilleSurveillanceDetailComponent>;
        const route = ({ data: of({ feuilleSurveillance: new FeuilleSurveillance(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [FeuilleSurveillanceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FeuilleSurveillanceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FeuilleSurveillanceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.feuilleSurveillance).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HospitalmanagerTestModule } from '../../../test.module';
import { FeuilleSurveillanceComponent } from 'app/entities/feuille-surveillance/feuille-surveillance.component';
import { FeuilleSurveillanceService } from 'app/entities/feuille-surveillance/feuille-surveillance.service';
import { FeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';

describe('Component Tests', () => {
    describe('FeuilleSurveillance Management Component', () => {
        let comp: FeuilleSurveillanceComponent;
        let fixture: ComponentFixture<FeuilleSurveillanceComponent>;
        let service: FeuilleSurveillanceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [FeuilleSurveillanceComponent],
                providers: []
            })
                .overrideTemplate(FeuilleSurveillanceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FeuilleSurveillanceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeuilleSurveillanceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FeuilleSurveillance(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.feuilleSurveillances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

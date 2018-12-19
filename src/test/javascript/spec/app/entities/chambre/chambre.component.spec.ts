/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HospitalmanagerTestModule } from '../../../test.module';
import { ChambreComponent } from 'app/entities/chambre/chambre.component';
import { ChambreService } from 'app/entities/chambre/chambre.service';
import { Chambre } from 'app/shared/model/chambre.model';

describe('Component Tests', () => {
    describe('Chambre Management Component', () => {
        let comp: ChambreComponent;
        let fixture: ComponentFixture<ChambreComponent>;
        let service: ChambreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [ChambreComponent],
                providers: []
            })
                .overrideTemplate(ChambreComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChambreComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChambreService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Chambre(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.chambres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

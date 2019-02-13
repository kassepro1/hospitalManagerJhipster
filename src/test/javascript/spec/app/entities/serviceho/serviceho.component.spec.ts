/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HospitalmanagerTestModule } from '../../../test.module';
import { ServicehoComponent } from 'app/entities/serviceho/serviceho.component';
import { ServicehoService } from 'app/entities/serviceho/serviceho.service';
import { Serviceho } from 'app/shared/model/serviceho.model';

describe('Component Tests', () => {
    describe('Serviceho Management Component', () => {
        let comp: ServicehoComponent;
        let fixture: ComponentFixture<ServicehoComponent>;
        let service: ServicehoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [ServicehoComponent],
                providers: []
            })
                .overrideTemplate(ServicehoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ServicehoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicehoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Serviceho(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.servicehos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

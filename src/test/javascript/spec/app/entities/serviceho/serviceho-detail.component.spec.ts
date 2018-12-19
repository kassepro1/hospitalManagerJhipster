/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { ServicehoDetailComponent } from 'app/entities/serviceho/serviceho-detail.component';
import { Serviceho } from 'app/shared/model/serviceho.model';

describe('Component Tests', () => {
    describe('Serviceho Management Detail Component', () => {
        let comp: ServicehoDetailComponent;
        let fixture: ComponentFixture<ServicehoDetailComponent>;
        const route = ({ data: of({ serviceho: new Serviceho(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [ServicehoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ServicehoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ServicehoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.serviceho).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

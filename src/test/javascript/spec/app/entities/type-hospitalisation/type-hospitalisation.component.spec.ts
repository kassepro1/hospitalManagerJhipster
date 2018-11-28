/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HospitalmanagerTestModule } from '../../../test.module';
import { TypeHospitalisationComponent } from 'app/entities/type-hospitalisation/type-hospitalisation.component';
import { TypeHospitalisationService } from 'app/entities/type-hospitalisation/type-hospitalisation.service';
import { TypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';

describe('Component Tests', () => {
    describe('TypeHospitalisation Management Component', () => {
        let comp: TypeHospitalisationComponent;
        let fixture: ComponentFixture<TypeHospitalisationComponent>;
        let service: TypeHospitalisationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [TypeHospitalisationComponent],
                providers: []
            })
                .overrideTemplate(TypeHospitalisationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeHospitalisationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeHospitalisationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TypeHospitalisation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.typeHospitalisations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

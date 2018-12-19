/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { TypeHospitalisationDetailComponent } from 'app/entities/type-hospitalisation/type-hospitalisation-detail.component';
import { TypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';

describe('Component Tests', () => {
    describe('TypeHospitalisation Management Detail Component', () => {
        let comp: TypeHospitalisationDetailComponent;
        let fixture: ComponentFixture<TypeHospitalisationDetailComponent>;
        const route = ({ data: of({ typeHospitalisation: new TypeHospitalisation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [TypeHospitalisationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeHospitalisationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeHospitalisationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeHospitalisation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

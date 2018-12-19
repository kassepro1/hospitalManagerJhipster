/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { TypeTraitementDetailComponent } from 'app/entities/type-traitement/type-traitement-detail.component';
import { TypeTraitement } from 'app/shared/model/type-traitement.model';

describe('Component Tests', () => {
    describe('TypeTraitement Management Detail Component', () => {
        let comp: TypeTraitementDetailComponent;
        let fixture: ComponentFixture<TypeTraitementDetailComponent>;
        const route = ({ data: of({ typeTraitement: new TypeTraitement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [TypeTraitementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeTraitementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeTraitementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeTraitement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

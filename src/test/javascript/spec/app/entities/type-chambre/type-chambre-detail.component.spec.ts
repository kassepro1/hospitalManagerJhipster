/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { TypeChambreDetailComponent } from 'app/entities/type-chambre/type-chambre-detail.component';
import { TypeChambre } from 'app/shared/model/type-chambre.model';

describe('Component Tests', () => {
    describe('TypeChambre Management Detail Component', () => {
        let comp: TypeChambreDetailComponent;
        let fixture: ComponentFixture<TypeChambreDetailComponent>;
        const route = ({ data: of({ typeChambre: new TypeChambre(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [TypeChambreDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeChambreDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeChambreDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeChambre).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

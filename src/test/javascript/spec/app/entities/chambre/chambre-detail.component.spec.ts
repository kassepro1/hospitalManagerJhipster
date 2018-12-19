/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HospitalmanagerTestModule } from '../../../test.module';
import { ChambreDetailComponent } from 'app/entities/chambre/chambre-detail.component';
import { Chambre } from 'app/shared/model/chambre.model';

describe('Component Tests', () => {
    describe('Chambre Management Detail Component', () => {
        let comp: ChambreDetailComponent;
        let fixture: ComponentFixture<ChambreDetailComponent>;
        const route = ({ data: of({ chambre: new Chambre(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [ChambreDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChambreDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChambreDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.chambre).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

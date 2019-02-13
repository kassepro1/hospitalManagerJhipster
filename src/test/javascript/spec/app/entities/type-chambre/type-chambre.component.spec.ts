/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HospitalmanagerTestModule } from '../../../test.module';
import { TypeChambreComponent } from 'app/entities/type-chambre/type-chambre.component';
import { TypeChambreService } from 'app/entities/type-chambre/type-chambre.service';
import { TypeChambre } from 'app/shared/model/type-chambre.model';

describe('Component Tests', () => {
    describe('TypeChambre Management Component', () => {
        let comp: TypeChambreComponent;
        let fixture: ComponentFixture<TypeChambreComponent>;
        let service: TypeChambreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [TypeChambreComponent],
                providers: []
            })
                .overrideTemplate(TypeChambreComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeChambreComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeChambreService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TypeChambre(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.typeChambres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

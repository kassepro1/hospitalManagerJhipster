/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HospitalmanagerTestModule } from '../../../test.module';
import { DocteurComponent } from 'app/entities/docteur/docteur.component';
import { DocteurService } from 'app/entities/docteur/docteur.service';
import { Docteur } from 'app/shared/model/docteur.model';

describe('Component Tests', () => {
    describe('Docteur Management Component', () => {
        let comp: DocteurComponent;
        let fixture: ComponentFixture<DocteurComponent>;
        let service: DocteurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [DocteurComponent],
                providers: []
            })
                .overrideTemplate(DocteurComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DocteurComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocteurService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Docteur(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.docteurs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

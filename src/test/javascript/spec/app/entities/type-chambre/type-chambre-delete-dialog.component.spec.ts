/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalmanagerTestModule } from '../../../test.module';
import { TypeChambreDeleteDialogComponent } from 'app/entities/type-chambre/type-chambre-delete-dialog.component';
import { TypeChambreService } from 'app/entities/type-chambre/type-chambre.service';

describe('Component Tests', () => {
    describe('TypeChambre Management Delete Component', () => {
        let comp: TypeChambreDeleteDialogComponent;
        let fixture: ComponentFixture<TypeChambreDeleteDialogComponent>;
        let service: TypeChambreService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [TypeChambreDeleteDialogComponent]
            })
                .overrideTemplate(TypeChambreDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeChambreDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeChambreService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

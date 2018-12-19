/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalmanagerTestModule } from '../../../test.module';
import { TypeHospitalisationDeleteDialogComponent } from 'app/entities/type-hospitalisation/type-hospitalisation-delete-dialog.component';
import { TypeHospitalisationService } from 'app/entities/type-hospitalisation/type-hospitalisation.service';

describe('Component Tests', () => {
    describe('TypeHospitalisation Management Delete Component', () => {
        let comp: TypeHospitalisationDeleteDialogComponent;
        let fixture: ComponentFixture<TypeHospitalisationDeleteDialogComponent>;
        let service: TypeHospitalisationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [TypeHospitalisationDeleteDialogComponent]
            })
                .overrideTemplate(TypeHospitalisationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeHospitalisationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeHospitalisationService);
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

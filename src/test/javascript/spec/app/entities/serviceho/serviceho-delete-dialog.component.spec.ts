/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalmanagerTestModule } from '../../../test.module';
import { ServicehoDeleteDialogComponent } from 'app/entities/serviceho/serviceho-delete-dialog.component';
import { ServicehoService } from 'app/entities/serviceho/serviceho.service';

describe('Component Tests', () => {
    describe('Serviceho Management Delete Component', () => {
        let comp: ServicehoDeleteDialogComponent;
        let fixture: ComponentFixture<ServicehoDeleteDialogComponent>;
        let service: ServicehoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [ServicehoDeleteDialogComponent]
            })
                .overrideTemplate(ServicehoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ServicehoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicehoService);
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

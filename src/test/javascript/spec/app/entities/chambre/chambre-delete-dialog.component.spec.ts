/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalmanagerTestModule } from '../../../test.module';
import { ChambreDeleteDialogComponent } from 'app/entities/chambre/chambre-delete-dialog.component';
import { ChambreService } from 'app/entities/chambre/chambre.service';

describe('Component Tests', () => {
    describe('Chambre Management Delete Component', () => {
        let comp: ChambreDeleteDialogComponent;
        let fixture: ComponentFixture<ChambreDeleteDialogComponent>;
        let service: ChambreService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [ChambreDeleteDialogComponent]
            })
                .overrideTemplate(ChambreDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChambreDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChambreService);
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

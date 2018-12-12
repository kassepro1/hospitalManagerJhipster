/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalmanagerTestModule } from '../../../test.module';
import { DocteurDeleteDialogComponent } from 'app/entities/docteur/docteur-delete-dialog.component';
import { DocteurService } from 'app/entities/docteur/docteur.service';

describe('Component Tests', () => {
    describe('Docteur Management Delete Component', () => {
        let comp: DocteurDeleteDialogComponent;
        let fixture: ComponentFixture<DocteurDeleteDialogComponent>;
        let service: DocteurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HospitalmanagerTestModule],
                declarations: [DocteurDeleteDialogComponent]
            })
                .overrideTemplate(DocteurDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocteurDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocteurService);
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

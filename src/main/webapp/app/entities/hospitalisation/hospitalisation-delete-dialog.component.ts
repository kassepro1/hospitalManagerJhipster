import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHospitalisation } from 'app/shared/model/hospitalisation.model';
import { HospitalisationService } from './hospitalisation.service';

@Component({
    selector: 'jhi-hospitalisation-delete-dialog',
    templateUrl: './hospitalisation-delete-dialog.component.html'
})
export class HospitalisationDeleteDialogComponent {
    hospitalisation: IHospitalisation;

    constructor(
        private hospitalisationService: HospitalisationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hospitalisationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'hospitalisationListModification',
                content: 'Deleted an hospitalisation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hospitalisation-delete-popup',
    template: ''
})
export class HospitalisationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hospitalisation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HospitalisationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.hospitalisation = hospitalisation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

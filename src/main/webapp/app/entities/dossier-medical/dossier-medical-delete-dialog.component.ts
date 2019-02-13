import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDossierMedical } from 'app/shared/model/dossier-medical.model';
import { DossierMedicalService } from './dossier-medical.service';

@Component({
    selector: 'jhi-dossier-medical-delete-dialog',
    templateUrl: './dossier-medical-delete-dialog.component.html'
})
export class DossierMedicalDeleteDialogComponent {
    dossierMedical: IDossierMedical;

    constructor(
        private dossierMedicalService: DossierMedicalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dossierMedicalService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dossierMedicalListModification',
                content: 'Deleted an dossierMedical'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dossier-medical-delete-popup',
    template: ''
})
export class DossierMedicalDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dossierMedical }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DossierMedicalDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dossierMedical = dossierMedical;
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

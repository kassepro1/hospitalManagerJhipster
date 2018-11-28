import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';
import { TypeHospitalisationService } from './type-hospitalisation.service';

@Component({
    selector: 'jhi-type-hospitalisation-delete-dialog',
    templateUrl: './type-hospitalisation-delete-dialog.component.html'
})
export class TypeHospitalisationDeleteDialogComponent {
    typeHospitalisation: ITypeHospitalisation;

    constructor(
        private typeHospitalisationService: TypeHospitalisationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeHospitalisationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeHospitalisationListModification',
                content: 'Deleted an typeHospitalisation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-hospitalisation-delete-popup',
    template: ''
})
export class TypeHospitalisationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeHospitalisation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeHospitalisationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeHospitalisation = typeHospitalisation;
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

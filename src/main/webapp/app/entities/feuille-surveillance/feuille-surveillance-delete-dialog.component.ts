import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';
import { FeuilleSurveillanceService } from './feuille-surveillance.service';

@Component({
    selector: 'jhi-feuille-surveillance-delete-dialog',
    templateUrl: './feuille-surveillance-delete-dialog.component.html'
})
export class FeuilleSurveillanceDeleteDialogComponent {
    feuilleSurveillance: IFeuilleSurveillance;

    constructor(
        private feuilleSurveillanceService: FeuilleSurveillanceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.feuilleSurveillanceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'feuilleSurveillanceListModification',
                content: 'Deleted an feuilleSurveillance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-feuille-surveillance-delete-popup',
    template: ''
})
export class FeuilleSurveillanceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ feuilleSurveillance }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FeuilleSurveillanceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.feuilleSurveillance = feuilleSurveillance;
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

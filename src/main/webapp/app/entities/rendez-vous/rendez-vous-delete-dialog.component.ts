import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRendezVous } from 'app/shared/model/rendez-vous.model';
import { RendezVousService } from './rendez-vous.service';

@Component({
    selector: 'jhi-rendez-vous-delete-dialog',
    templateUrl: './rendez-vous-delete-dialog.component.html'
})
export class RendezVousDeleteDialogComponent {
    rendezVous: IRendezVous;

    constructor(private rendezVousService: RendezVousService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rendezVousService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rendezVousListModification',
                content: 'Deleted an rendezVous'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rendez-vous-delete-popup',
    template: ''
})
export class RendezVousDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rendezVous }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RendezVousDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.rendezVous = rendezVous;
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

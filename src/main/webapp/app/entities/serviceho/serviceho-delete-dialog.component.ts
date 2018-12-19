import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceho } from 'app/shared/model/serviceho.model';
import { ServicehoService } from './serviceho.service';

@Component({
    selector: 'jhi-serviceho-delete-dialog',
    templateUrl: './serviceho-delete-dialog.component.html'
})
export class ServicehoDeleteDialogComponent {
    serviceho: IServiceho;

    constructor(private servicehoService: ServicehoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.servicehoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'servicehoListModification',
                content: 'Deleted an serviceho'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-serviceho-delete-popup',
    template: ''
})
export class ServicehoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ serviceho }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ServicehoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.serviceho = serviceho;
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

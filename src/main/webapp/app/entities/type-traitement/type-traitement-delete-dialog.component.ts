import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeTraitement } from 'app/shared/model/type-traitement.model';
import { TypeTraitementService } from './type-traitement.service';

@Component({
    selector: 'jhi-type-traitement-delete-dialog',
    templateUrl: './type-traitement-delete-dialog.component.html'
})
export class TypeTraitementDeleteDialogComponent {
    typeTraitement: ITypeTraitement;

    constructor(
        private typeTraitementService: TypeTraitementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeTraitementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeTraitementListModification',
                content: 'Deleted an typeTraitement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-traitement-delete-popup',
    template: ''
})
export class TypeTraitementDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeTraitement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeTraitementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeTraitement = typeTraitement;
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

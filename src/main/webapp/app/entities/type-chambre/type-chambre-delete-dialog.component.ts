import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeChambre } from 'app/shared/model/type-chambre.model';
import { TypeChambreService } from './type-chambre.service';

@Component({
    selector: 'jhi-type-chambre-delete-dialog',
    templateUrl: './type-chambre-delete-dialog.component.html'
})
export class TypeChambreDeleteDialogComponent {
    typeChambre: ITypeChambre;

    constructor(
        private typeChambreService: TypeChambreService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeChambreService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeChambreListModification',
                content: 'Deleted an typeChambre'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-chambre-delete-popup',
    template: ''
})
export class TypeChambreDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeChambre }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeChambreDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeChambre = typeChambre;
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

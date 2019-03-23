import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICapsuleImage } from 'app/shared/model/capsule-image.model';
import { CapsuleImageService } from './capsule-image.service';

@Component({
    selector: 'jhi-capsule-image-delete-dialog',
    templateUrl: './capsule-image-delete-dialog.component.html'
})
export class CapsuleImageDeleteDialogComponent {
    capsuleImage: ICapsuleImage;

    constructor(
        protected capsuleImageService: CapsuleImageService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.capsuleImageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'capsuleImageListModification',
                content: 'Deleted an capsuleImage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-capsule-image-delete-popup',
    template: ''
})
export class CapsuleImageDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ capsuleImage }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CapsuleImageDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.capsuleImage = capsuleImage;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/capsule-image', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/capsule-image', { outlets: { popup: null } }]);
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

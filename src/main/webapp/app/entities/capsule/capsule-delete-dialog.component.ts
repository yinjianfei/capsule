import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICapsule } from 'app/shared/model/capsule.model';
import { CapsuleService } from './capsule.service';

@Component({
    selector: 'jhi-capsule-delete-dialog',
    templateUrl: './capsule-delete-dialog.component.html'
})
export class CapsuleDeleteDialogComponent {
    capsule: ICapsule;

    constructor(protected capsuleService: CapsuleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.capsuleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'capsuleListModification',
                content: 'Deleted an capsule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-capsule-delete-popup',
    template: ''
})
export class CapsuleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ capsule }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CapsuleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.capsule = capsule;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/capsule', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/capsule', { outlets: { popup: null } }]);
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

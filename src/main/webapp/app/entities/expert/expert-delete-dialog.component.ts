import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpert } from 'app/shared/model/expert.model';
import { ExpertService } from './expert.service';

@Component({
    selector: 'jhi-expert-delete-dialog',
    templateUrl: './expert-delete-dialog.component.html'
})
export class ExpertDeleteDialogComponent {
    expert: IExpert;

    constructor(protected expertService: ExpertService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.expertService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'expertListModification',
                content: 'Deleted an expert'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-expert-delete-popup',
    template: ''
})
export class ExpertDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ expert }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExpertDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.expert = expert;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/expert', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/expert', { outlets: { popup: null } }]);
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

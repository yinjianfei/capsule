import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExaminationReading } from 'app/shared/model/examination-reading.model';
import { ExaminationReadingService } from './examination-reading.service';

@Component({
    selector: 'jhi-examination-reading-delete-dialog',
    templateUrl: './examination-reading-delete-dialog.component.html'
})
export class ExaminationReadingDeleteDialogComponent {
    examinationReading: IExaminationReading;

    constructor(
        protected examinationReadingService: ExaminationReadingService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.examinationReadingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'examinationReadingListModification',
                content: 'Deleted an examinationReading'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-examination-reading-delete-popup',
    template: ''
})
export class ExaminationReadingDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ examinationReading }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExaminationReadingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.examinationReading = examinationReading;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/examination-reading', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/examination-reading', { outlets: { popup: null } }]);
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

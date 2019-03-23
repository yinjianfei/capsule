import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExamination } from 'app/shared/model/examination.model';
import { ExaminationService } from './examination.service';
import { ICapsule } from 'app/shared/model/capsule.model';
import { CapsuleService } from 'app/entities/capsule';

@Component({
    selector: 'jhi-examination-update',
    templateUrl: './examination-update.component.html'
})
export class ExaminationUpdateComponent implements OnInit {
    examination: IExamination;
    isSaving: boolean;

    capsules: ICapsule[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected examinationService: ExaminationService,
        protected capsuleService: CapsuleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ examination }) => {
            this.examination = examination;
        });
        this.capsuleService
            .query({ filter: 'examination-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICapsule[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICapsule[]>) => response.body)
            )
            .subscribe(
                (res: ICapsule[]) => {
                    if (!this.examination.capsuleId) {
                        this.capsules = res;
                    } else {
                        this.capsuleService
                            .find(this.examination.capsuleId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICapsule>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICapsule>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICapsule) => (this.capsules = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.examination.id !== undefined) {
            this.subscribeToSaveResponse(this.examinationService.update(this.examination));
        } else {
            this.subscribeToSaveResponse(this.examinationService.create(this.examination));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamination>>) {
        result.subscribe((res: HttpResponse<IExamination>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCapsuleById(index: number, item: ICapsule) {
        return item.id;
    }
}

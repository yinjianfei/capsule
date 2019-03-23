import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { IExamination } from 'app/shared/model/examination.model';
import { ExaminationService } from 'app/entities/examination';
import { ICapsuleImage } from 'app/shared/model/capsule-image.model';
import { CapsuleImageService } from 'app/entities/capsule-image';
import { IExaminationReading } from 'app/shared/model/examination-reading.model';
import { ExaminationReadingService } from 'app/entities/examination-reading';

@Component({
    selector: 'jhi-comment-update',
    templateUrl: './comment-update.component.html'
})
export class CommentUpdateComponent implements OnInit {
    comment: IComment;
    isSaving: boolean;

    examinations: IExamination[];

    capsuleimages: ICapsuleImage[];

    examinationreadings: IExaminationReading[];
    commentAt: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commentService: CommentService,
        protected examinationService: ExaminationService,
        protected capsuleImageService: CapsuleImageService,
        protected examinationReadingService: ExaminationReadingService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ comment }) => {
            this.comment = comment;
            this.commentAt = this.comment.commentAt != null ? this.comment.commentAt.format(DATE_TIME_FORMAT) : null;
        });
        this.examinationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExamination[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExamination[]>) => response.body)
            )
            .subscribe((res: IExamination[]) => (this.examinations = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.capsuleImageService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICapsuleImage[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICapsuleImage[]>) => response.body)
            )
            .subscribe((res: ICapsuleImage[]) => (this.capsuleimages = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.examinationReadingService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExaminationReading[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExaminationReading[]>) => response.body)
            )
            .subscribe(
                (res: IExaminationReading[]) => (this.examinationreadings = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.comment.commentAt = this.commentAt != null ? moment(this.commentAt, DATE_TIME_FORMAT) : null;
        if (this.comment.id !== undefined) {
            this.subscribeToSaveResponse(this.commentService.update(this.comment));
        } else {
            this.subscribeToSaveResponse(this.commentService.create(this.comment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>) {
        result.subscribe((res: HttpResponse<IComment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackExaminationById(index: number, item: IExamination) {
        return item.id;
    }

    trackCapsuleImageById(index: number, item: ICapsuleImage) {
        return item.id;
    }

    trackExaminationReadingById(index: number, item: IExaminationReading) {
        return item.id;
    }
}

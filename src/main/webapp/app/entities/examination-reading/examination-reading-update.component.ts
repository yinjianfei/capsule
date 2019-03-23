import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IExaminationReading } from 'app/shared/model/examination-reading.model';
import { ExaminationReadingService } from './examination-reading.service';

@Component({
    selector: 'jhi-examination-reading-update',
    templateUrl: './examination-reading-update.component.html'
})
export class ExaminationReadingUpdateComponent implements OnInit {
    examinationReading: IExaminationReading;
    isSaving: boolean;

    constructor(protected examinationReadingService: ExaminationReadingService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ examinationReading }) => {
            this.examinationReading = examinationReading;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.examinationReading.id !== undefined) {
            this.subscribeToSaveResponse(this.examinationReadingService.update(this.examinationReading));
        } else {
            this.subscribeToSaveResponse(this.examinationReadingService.create(this.examinationReading));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExaminationReading>>) {
        result.subscribe((res: HttpResponse<IExaminationReading>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

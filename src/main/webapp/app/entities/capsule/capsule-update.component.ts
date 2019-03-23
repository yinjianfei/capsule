import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICapsule } from 'app/shared/model/capsule.model';
import { CapsuleService } from './capsule.service';
import { IExamination } from 'app/shared/model/examination.model';
import { ExaminationService } from 'app/entities/examination';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';

@Component({
    selector: 'jhi-capsule-update',
    templateUrl: './capsule-update.component.html'
})
export class CapsuleUpdateComponent implements OnInit {
    capsule: ICapsule;
    isSaving: boolean;

    examinations: IExamination[];

    patients: IPatient[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected capsuleService: CapsuleService,
        protected examinationService: ExaminationService,
        protected patientService: PatientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ capsule }) => {
            this.capsule = capsule;
        });
        this.examinationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExamination[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExamination[]>) => response.body)
            )
            .subscribe((res: IExamination[]) => (this.examinations = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.patientService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPatient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPatient[]>) => response.body)
            )
            .subscribe((res: IPatient[]) => (this.patients = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.capsule.id !== undefined) {
            this.subscribeToSaveResponse(this.capsuleService.update(this.capsule));
        } else {
            this.subscribeToSaveResponse(this.capsuleService.create(this.capsule));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICapsule>>) {
        result.subscribe((res: HttpResponse<ICapsule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPatientById(index: number, item: IPatient) {
        return item.id;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICapsuleImage } from 'app/shared/model/capsule-image.model';
import { CapsuleImageService } from './capsule-image.service';
import { ICapsule } from 'app/shared/model/capsule.model';
import { CapsuleService } from 'app/entities/capsule';

@Component({
    selector: 'jhi-capsule-image-update',
    templateUrl: './capsule-image-update.component.html'
})
export class CapsuleImageUpdateComponent implements OnInit {
    capsuleImage: ICapsuleImage;
    isSaving: boolean;

    capsules: ICapsule[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected capsuleImageService: CapsuleImageService,
        protected capsuleService: CapsuleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ capsuleImage }) => {
            this.capsuleImage = capsuleImage;
        });
        this.capsuleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICapsule[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICapsule[]>) => response.body)
            )
            .subscribe((res: ICapsule[]) => (this.capsules = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.capsuleImage.id !== undefined) {
            this.subscribeToSaveResponse(this.capsuleImageService.update(this.capsuleImage));
        } else {
            this.subscribeToSaveResponse(this.capsuleImageService.create(this.capsuleImage));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICapsuleImage>>) {
        result.subscribe((res: HttpResponse<ICapsuleImage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

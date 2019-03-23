import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IExpert } from 'app/shared/model/expert.model';
import { ExpertService } from './expert.service';

@Component({
    selector: 'jhi-expert-update',
    templateUrl: './expert-update.component.html'
})
export class ExpertUpdateComponent implements OnInit {
    expert: IExpert;
    isSaving: boolean;

    constructor(protected expertService: ExpertService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ expert }) => {
            this.expert = expert;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.expert.id !== undefined) {
            this.subscribeToSaveResponse(this.expertService.update(this.expert));
        } else {
            this.subscribeToSaveResponse(this.expertService.create(this.expert));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpert>>) {
        result.subscribe((res: HttpResponse<IExpert>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

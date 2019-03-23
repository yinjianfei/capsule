import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExaminationReading } from 'app/shared/model/examination-reading.model';

@Component({
    selector: 'jhi-examination-reading-detail',
    templateUrl: './examination-reading-detail.component.html'
})
export class ExaminationReadingDetailComponent implements OnInit {
    examinationReading: IExaminationReading;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ examinationReading }) => {
            this.examinationReading = examinationReading;
        });
    }

    previousState() {
        window.history.back();
    }
}

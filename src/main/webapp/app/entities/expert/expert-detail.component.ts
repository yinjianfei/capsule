import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExpert } from 'app/shared/model/expert.model';

@Component({
    selector: 'jhi-expert-detail',
    templateUrl: './expert-detail.component.html'
})
export class ExpertDetailComponent implements OnInit {
    expert: IExpert;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ expert }) => {
            this.expert = expert;
        });
    }

    previousState() {
        window.history.back();
    }
}

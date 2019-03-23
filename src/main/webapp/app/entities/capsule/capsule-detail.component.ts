import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICapsule } from 'app/shared/model/capsule.model';

@Component({
    selector: 'jhi-capsule-detail',
    templateUrl: './capsule-detail.component.html'
})
export class CapsuleDetailComponent implements OnInit {
    capsule: ICapsule;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ capsule }) => {
            this.capsule = capsule;
        });
    }

    previousState() {
        window.history.back();
    }
}

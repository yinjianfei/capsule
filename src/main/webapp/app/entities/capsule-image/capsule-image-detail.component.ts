import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICapsuleImage } from 'app/shared/model/capsule-image.model';

@Component({
    selector: 'jhi-capsule-image-detail',
    templateUrl: './capsule-image-detail.component.html'
})
export class CapsuleImageDetailComponent implements OnInit {
    capsuleImage: ICapsuleImage;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ capsuleImage }) => {
            this.capsuleImage = capsuleImage;
        });
    }

    previousState() {
        window.history.back();
    }
}

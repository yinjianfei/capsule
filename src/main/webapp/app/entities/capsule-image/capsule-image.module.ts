import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CapsuleSharedModule } from 'app/shared';
import {
    CapsuleImageComponent,
    CapsuleImageDetailComponent,
    CapsuleImageUpdateComponent,
    CapsuleImageDeletePopupComponent,
    CapsuleImageDeleteDialogComponent,
    capsuleImageRoute,
    capsuleImagePopupRoute
} from './';

const ENTITY_STATES = [...capsuleImageRoute, ...capsuleImagePopupRoute];

@NgModule({
    imports: [CapsuleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CapsuleImageComponent,
        CapsuleImageDetailComponent,
        CapsuleImageUpdateComponent,
        CapsuleImageDeleteDialogComponent,
        CapsuleImageDeletePopupComponent
    ],
    entryComponents: [
        CapsuleImageComponent,
        CapsuleImageUpdateComponent,
        CapsuleImageDeleteDialogComponent,
        CapsuleImageDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapsuleCapsuleImageModule {}

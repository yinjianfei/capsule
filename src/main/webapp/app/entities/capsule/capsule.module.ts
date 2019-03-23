import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CapsuleSharedModule } from 'app/shared';
import {
    CapsuleComponent,
    CapsuleDetailComponent,
    CapsuleUpdateComponent,
    CapsuleDeletePopupComponent,
    CapsuleDeleteDialogComponent,
    capsuleRoute,
    capsulePopupRoute
} from './';

const ENTITY_STATES = [...capsuleRoute, ...capsulePopupRoute];

@NgModule({
    imports: [CapsuleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CapsuleComponent,
        CapsuleDetailComponent,
        CapsuleUpdateComponent,
        CapsuleDeleteDialogComponent,
        CapsuleDeletePopupComponent
    ],
    entryComponents: [CapsuleComponent, CapsuleUpdateComponent, CapsuleDeleteDialogComponent, CapsuleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapsuleCapsuleModule {}

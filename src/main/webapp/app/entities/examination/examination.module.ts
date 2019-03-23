import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CapsuleSharedModule } from 'app/shared';
import {
    ExaminationComponent,
    ExaminationDetailComponent,
    ExaminationUpdateComponent,
    ExaminationDeletePopupComponent,
    ExaminationDeleteDialogComponent,
    examinationRoute,
    examinationPopupRoute
} from './';

const ENTITY_STATES = [...examinationRoute, ...examinationPopupRoute];

@NgModule({
    imports: [CapsuleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExaminationComponent,
        ExaminationDetailComponent,
        ExaminationUpdateComponent,
        ExaminationDeleteDialogComponent,
        ExaminationDeletePopupComponent
    ],
    entryComponents: [ExaminationComponent, ExaminationUpdateComponent, ExaminationDeleteDialogComponent, ExaminationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapsuleExaminationModule {}

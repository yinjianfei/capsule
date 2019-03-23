import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CapsuleSharedModule } from 'app/shared';
import {
    ExaminationReadingComponent,
    ExaminationReadingDetailComponent,
    ExaminationReadingUpdateComponent,
    ExaminationReadingDeletePopupComponent,
    ExaminationReadingDeleteDialogComponent,
    examinationReadingRoute,
    examinationReadingPopupRoute
} from './';

const ENTITY_STATES = [...examinationReadingRoute, ...examinationReadingPopupRoute];

@NgModule({
    imports: [CapsuleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExaminationReadingComponent,
        ExaminationReadingDetailComponent,
        ExaminationReadingUpdateComponent,
        ExaminationReadingDeleteDialogComponent,
        ExaminationReadingDeletePopupComponent
    ],
    entryComponents: [
        ExaminationReadingComponent,
        ExaminationReadingUpdateComponent,
        ExaminationReadingDeleteDialogComponent,
        ExaminationReadingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapsuleExaminationReadingModule {}

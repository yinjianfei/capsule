import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CapsuleSharedModule } from 'app/shared';
import {
    ExpertComponent,
    ExpertDetailComponent,
    ExpertUpdateComponent,
    ExpertDeletePopupComponent,
    ExpertDeleteDialogComponent,
    expertRoute,
    expertPopupRoute
} from './';

const ENTITY_STATES = [...expertRoute, ...expertPopupRoute];

@NgModule({
    imports: [CapsuleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ExpertComponent, ExpertDetailComponent, ExpertUpdateComponent, ExpertDeleteDialogComponent, ExpertDeletePopupComponent],
    entryComponents: [ExpertComponent, ExpertUpdateComponent, ExpertDeleteDialogComponent, ExpertDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapsuleExpertModule {}

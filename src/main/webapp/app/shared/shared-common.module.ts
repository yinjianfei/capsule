import { NgModule } from '@angular/core';

import { CapsuleSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [CapsuleSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [CapsuleSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CapsuleSharedCommonModule {}

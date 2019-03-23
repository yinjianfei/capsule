import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'patient',
                loadChildren: './patient/patient.module#CapsulePatientModule'
            },
            {
                path: 'doctor',
                loadChildren: './doctor/doctor.module#CapsuleDoctorModule'
            },
            {
                path: 'expert',
                loadChildren: './expert/expert.module#CapsuleExpertModule'
            },
            {
                path: 'capsule',
                loadChildren: './capsule/capsule.module#CapsuleCapsuleModule'
            },
            {
                path: 'examination',
                loadChildren: './examination/examination.module#CapsuleExaminationModule'
            },
            {
                path: 'capsule-image',
                loadChildren: './capsule-image/capsule-image.module#CapsuleCapsuleImageModule'
            },
            {
                path: 'comment',
                loadChildren: './comment/comment.module#CapsuleCommentModule'
            },
            {
                path: 'examination-reading',
                loadChildren: './examination-reading/examination-reading.module#CapsuleExaminationReadingModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapsuleEntityModule {}

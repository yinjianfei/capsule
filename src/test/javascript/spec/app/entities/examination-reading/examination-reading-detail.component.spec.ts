/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CapsuleTestModule } from '../../../test.module';
import { ExaminationReadingDetailComponent } from 'app/entities/examination-reading/examination-reading-detail.component';
import { ExaminationReading } from 'app/shared/model/examination-reading.model';

describe('Component Tests', () => {
    describe('ExaminationReading Management Detail Component', () => {
        let comp: ExaminationReadingDetailComponent;
        let fixture: ComponentFixture<ExaminationReadingDetailComponent>;
        const route = ({ data: of({ examinationReading: new ExaminationReading(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [ExaminationReadingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExaminationReadingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExaminationReadingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.examinationReading).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

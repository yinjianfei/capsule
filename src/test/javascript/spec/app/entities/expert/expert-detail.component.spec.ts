/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CapsuleTestModule } from '../../../test.module';
import { ExpertDetailComponent } from 'app/entities/expert/expert-detail.component';
import { Expert } from 'app/shared/model/expert.model';

describe('Component Tests', () => {
    describe('Expert Management Detail Component', () => {
        let comp: ExpertDetailComponent;
        let fixture: ComponentFixture<ExpertDetailComponent>;
        const route = ({ data: of({ expert: new Expert(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [ExpertDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExpertDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExpertDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.expert).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

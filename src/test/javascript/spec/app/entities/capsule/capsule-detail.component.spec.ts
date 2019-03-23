/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CapsuleTestModule } from '../../../test.module';
import { CapsuleDetailComponent } from 'app/entities/capsule/capsule-detail.component';
import { Capsule } from 'app/shared/model/capsule.model';

describe('Component Tests', () => {
    describe('Capsule Management Detail Component', () => {
        let comp: CapsuleDetailComponent;
        let fixture: ComponentFixture<CapsuleDetailComponent>;
        const route = ({ data: of({ capsule: new Capsule(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [CapsuleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CapsuleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CapsuleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.capsule).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

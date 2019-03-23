/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CapsuleTestModule } from '../../../test.module';
import { CapsuleImageDetailComponent } from 'app/entities/capsule-image/capsule-image-detail.component';
import { CapsuleImage } from 'app/shared/model/capsule-image.model';

describe('Component Tests', () => {
    describe('CapsuleImage Management Detail Component', () => {
        let comp: CapsuleImageDetailComponent;
        let fixture: ComponentFixture<CapsuleImageDetailComponent>;
        const route = ({ data: of({ capsuleImage: new CapsuleImage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [CapsuleImageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CapsuleImageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CapsuleImageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.capsuleImage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

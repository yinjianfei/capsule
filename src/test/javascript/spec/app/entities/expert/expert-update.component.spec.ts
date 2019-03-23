/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CapsuleTestModule } from '../../../test.module';
import { ExpertUpdateComponent } from 'app/entities/expert/expert-update.component';
import { ExpertService } from 'app/entities/expert/expert.service';
import { Expert } from 'app/shared/model/expert.model';

describe('Component Tests', () => {
    describe('Expert Management Update Component', () => {
        let comp: ExpertUpdateComponent;
        let fixture: ComponentFixture<ExpertUpdateComponent>;
        let service: ExpertService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [ExpertUpdateComponent]
            })
                .overrideTemplate(ExpertUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExpertUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExpertService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Expert(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.expert = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Expert();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.expert = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

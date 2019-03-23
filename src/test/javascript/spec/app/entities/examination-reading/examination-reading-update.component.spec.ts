/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CapsuleTestModule } from '../../../test.module';
import { ExaminationReadingUpdateComponent } from 'app/entities/examination-reading/examination-reading-update.component';
import { ExaminationReadingService } from 'app/entities/examination-reading/examination-reading.service';
import { ExaminationReading } from 'app/shared/model/examination-reading.model';

describe('Component Tests', () => {
    describe('ExaminationReading Management Update Component', () => {
        let comp: ExaminationReadingUpdateComponent;
        let fixture: ComponentFixture<ExaminationReadingUpdateComponent>;
        let service: ExaminationReadingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [ExaminationReadingUpdateComponent]
            })
                .overrideTemplate(ExaminationReadingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExaminationReadingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExaminationReadingService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExaminationReading(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.examinationReading = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExaminationReading();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.examinationReading = entity;
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

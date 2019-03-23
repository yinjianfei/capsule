/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CapsuleTestModule } from '../../../test.module';
import { ExaminationReadingDeleteDialogComponent } from 'app/entities/examination-reading/examination-reading-delete-dialog.component';
import { ExaminationReadingService } from 'app/entities/examination-reading/examination-reading.service';

describe('Component Tests', () => {
    describe('ExaminationReading Management Delete Component', () => {
        let comp: ExaminationReadingDeleteDialogComponent;
        let fixture: ComponentFixture<ExaminationReadingDeleteDialogComponent>;
        let service: ExaminationReadingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [ExaminationReadingDeleteDialogComponent]
            })
                .overrideTemplate(ExaminationReadingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExaminationReadingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExaminationReadingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

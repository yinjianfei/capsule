/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CapsuleTestModule } from '../../../test.module';
import { ExpertDeleteDialogComponent } from 'app/entities/expert/expert-delete-dialog.component';
import { ExpertService } from 'app/entities/expert/expert.service';

describe('Component Tests', () => {
    describe('Expert Management Delete Component', () => {
        let comp: ExpertDeleteDialogComponent;
        let fixture: ComponentFixture<ExpertDeleteDialogComponent>;
        let service: ExpertService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [ExpertDeleteDialogComponent]
            })
                .overrideTemplate(ExpertDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExpertDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExpertService);
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

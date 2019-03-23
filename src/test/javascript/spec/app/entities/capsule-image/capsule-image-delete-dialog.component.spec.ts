/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CapsuleTestModule } from '../../../test.module';
import { CapsuleImageDeleteDialogComponent } from 'app/entities/capsule-image/capsule-image-delete-dialog.component';
import { CapsuleImageService } from 'app/entities/capsule-image/capsule-image.service';

describe('Component Tests', () => {
    describe('CapsuleImage Management Delete Component', () => {
        let comp: CapsuleImageDeleteDialogComponent;
        let fixture: ComponentFixture<CapsuleImageDeleteDialogComponent>;
        let service: CapsuleImageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [CapsuleImageDeleteDialogComponent]
            })
                .overrideTemplate(CapsuleImageDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CapsuleImageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CapsuleImageService);
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

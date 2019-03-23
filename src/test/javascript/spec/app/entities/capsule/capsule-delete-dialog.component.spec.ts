/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CapsuleTestModule } from '../../../test.module';
import { CapsuleDeleteDialogComponent } from 'app/entities/capsule/capsule-delete-dialog.component';
import { CapsuleService } from 'app/entities/capsule/capsule.service';

describe('Component Tests', () => {
    describe('Capsule Management Delete Component', () => {
        let comp: CapsuleDeleteDialogComponent;
        let fixture: ComponentFixture<CapsuleDeleteDialogComponent>;
        let service: CapsuleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [CapsuleDeleteDialogComponent]
            })
                .overrideTemplate(CapsuleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CapsuleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CapsuleService);
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

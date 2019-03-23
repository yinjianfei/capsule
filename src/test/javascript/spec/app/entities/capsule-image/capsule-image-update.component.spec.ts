/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CapsuleTestModule } from '../../../test.module';
import { CapsuleImageUpdateComponent } from 'app/entities/capsule-image/capsule-image-update.component';
import { CapsuleImageService } from 'app/entities/capsule-image/capsule-image.service';
import { CapsuleImage } from 'app/shared/model/capsule-image.model';

describe('Component Tests', () => {
    describe('CapsuleImage Management Update Component', () => {
        let comp: CapsuleImageUpdateComponent;
        let fixture: ComponentFixture<CapsuleImageUpdateComponent>;
        let service: CapsuleImageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [CapsuleImageUpdateComponent]
            })
                .overrideTemplate(CapsuleImageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CapsuleImageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CapsuleImageService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CapsuleImage(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.capsuleImage = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CapsuleImage();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.capsuleImage = entity;
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

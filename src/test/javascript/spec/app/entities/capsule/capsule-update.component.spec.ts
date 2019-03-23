/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CapsuleTestModule } from '../../../test.module';
import { CapsuleUpdateComponent } from 'app/entities/capsule/capsule-update.component';
import { CapsuleService } from 'app/entities/capsule/capsule.service';
import { Capsule } from 'app/shared/model/capsule.model';

describe('Component Tests', () => {
    describe('Capsule Management Update Component', () => {
        let comp: CapsuleUpdateComponent;
        let fixture: ComponentFixture<CapsuleUpdateComponent>;
        let service: CapsuleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapsuleTestModule],
                declarations: [CapsuleUpdateComponent]
            })
                .overrideTemplate(CapsuleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CapsuleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CapsuleService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Capsule(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.capsule = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Capsule();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.capsule = entity;
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

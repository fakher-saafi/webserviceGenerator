/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { App3TestModule } from '../../../test.module';
import { MyColumnUpdateComponent } from 'app/entities/my-column/my-column-update.component';
import { MyColumnService } from 'app/entities/my-column/my-column.service';
import { MyColumn } from 'app/shared/model/my-column.model';

describe('Component Tests', () => {
    describe('MyColumn Management Update Component', () => {
        let comp: MyColumnUpdateComponent;
        let fixture: ComponentFixture<MyColumnUpdateComponent>;
        let service: MyColumnService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [App3TestModule],
                declarations: [MyColumnUpdateComponent]
            })
                .overrideTemplate(MyColumnUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MyColumnUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MyColumnService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MyColumn(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.myColumn = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MyColumn();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.myColumn = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

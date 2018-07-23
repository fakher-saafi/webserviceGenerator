/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { App3TestModule } from '../../../test.module';
import { MyTableUpdateComponent } from 'app/entities/my-table/my-table-update.component';
import { MyTableService } from 'app/entities/my-table/my-table.service';
import { MyTable } from 'app/shared/model/my-table.model';

describe('Component Tests', () => {
    describe('MyTable Management Update Component', () => {
        let comp: MyTableUpdateComponent;
        let fixture: ComponentFixture<MyTableUpdateComponent>;
        let service: MyTableService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [App3TestModule],
                declarations: [MyTableUpdateComponent]
            })
                .overrideTemplate(MyTableUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MyTableUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MyTableService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MyTable(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.myTable = entity;
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
                    const entity = new MyTable();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.myTable = entity;
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

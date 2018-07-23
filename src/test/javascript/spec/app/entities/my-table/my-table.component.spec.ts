/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { App3TestModule } from '../../../test.module';
import { MyTableComponent } from 'app/entities/my-table/my-table.component';
import { MyTableService } from 'app/entities/my-table/my-table.service';
import { MyTable } from 'app/shared/model/my-table.model';

describe('Component Tests', () => {
    describe('MyTable Management Component', () => {
        let comp: MyTableComponent;
        let fixture: ComponentFixture<MyTableComponent>;
        let service: MyTableService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [App3TestModule],
                declarations: [MyTableComponent],
                providers: []
            })
                .overrideTemplate(MyTableComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MyTableComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MyTableService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MyTable(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.myTables[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

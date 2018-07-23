/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { App3TestModule } from '../../../test.module';
import { MyColumnComponent } from 'app/entities/my-column/my-column.component';
import { MyColumnService } from 'app/entities/my-column/my-column.service';
import { MyColumn } from 'app/shared/model/my-column.model';

describe('Component Tests', () => {
    describe('MyColumn Management Component', () => {
        let comp: MyColumnComponent;
        let fixture: ComponentFixture<MyColumnComponent>;
        let service: MyColumnService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [App3TestModule],
                declarations: [MyColumnComponent],
                providers: []
            })
                .overrideTemplate(MyColumnComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MyColumnComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MyColumnService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MyColumn(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.myColumns[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

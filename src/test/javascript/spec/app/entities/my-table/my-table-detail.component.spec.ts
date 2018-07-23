/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { App3TestModule } from '../../../test.module';
import { MyTableDetailComponent } from 'app/entities/my-table/my-table-detail.component';
import { MyTable } from 'app/shared/model/my-table.model';

describe('Component Tests', () => {
    describe('MyTable Management Detail Component', () => {
        let comp: MyTableDetailComponent;
        let fixture: ComponentFixture<MyTableDetailComponent>;
        const route = ({ data: of({ myTable: new MyTable(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [App3TestModule],
                declarations: [MyTableDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MyTableDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MyTableDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.myTable).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

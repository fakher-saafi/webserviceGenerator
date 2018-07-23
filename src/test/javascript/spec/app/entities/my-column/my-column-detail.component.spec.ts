/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { App3TestModule } from '../../../test.module';
import { MyColumnDetailComponent } from 'app/entities/my-column/my-column-detail.component';
import { MyColumn } from 'app/shared/model/my-column.model';

describe('Component Tests', () => {
    describe('MyColumn Management Detail Component', () => {
        let comp: MyColumnDetailComponent;
        let fixture: ComponentFixture<MyColumnDetailComponent>;
        const route = ({ data: of({ myColumn: new MyColumn(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [App3TestModule],
                declarations: [MyColumnDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MyColumnDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MyColumnDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.myColumn).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

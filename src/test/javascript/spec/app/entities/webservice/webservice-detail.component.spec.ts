/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { App3TestModule } from '../../../test.module';
import { WebserviceDetailComponent } from 'app/entities/webservice/webservice-detail.component';
import { Webservice } from 'app/shared/model/webservice.model';

describe('Component Tests', () => {
    describe('Webservice Management Detail Component', () => {
        let comp: WebserviceDetailComponent;
        let fixture: ComponentFixture<WebserviceDetailComponent>;
        const route = ({ data: of({ webservice: new Webservice(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [App3TestModule],
                declarations: [WebserviceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WebserviceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WebserviceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.webservice).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

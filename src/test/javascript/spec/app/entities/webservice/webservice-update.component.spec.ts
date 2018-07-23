/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { App3TestModule } from '../../../test.module';
import { WebserviceUpdateComponent } from 'app/entities/webservice/webservice-update.component';
import { WebserviceService } from 'app/entities/webservice/webservice.service';
import { Webservice } from 'app/shared/model/webservice.model';

describe('Component Tests', () => {
    describe('Webservice Management Update Component', () => {
        let comp: WebserviceUpdateComponent;
        let fixture: ComponentFixture<WebserviceUpdateComponent>;
        let service: WebserviceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [App3TestModule],
                declarations: [WebserviceUpdateComponent]
            })
                .overrideTemplate(WebserviceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WebserviceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WebserviceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Webservice(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.webservice = entity;
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
                    const entity = new Webservice();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.webservice = entity;
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

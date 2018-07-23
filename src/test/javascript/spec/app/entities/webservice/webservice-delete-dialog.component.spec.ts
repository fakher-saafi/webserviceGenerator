/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { App3TestModule } from '../../../test.module';
import { WebserviceDeleteDialogComponent } from 'app/entities/webservice/webservice-delete-dialog.component';
import { WebserviceService } from 'app/entities/webservice/webservice.service';

describe('Component Tests', () => {
    describe('Webservice Management Delete Component', () => {
        let comp: WebserviceDeleteDialogComponent;
        let fixture: ComponentFixture<WebserviceDeleteDialogComponent>;
        let service: WebserviceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [App3TestModule],
                declarations: [WebserviceDeleteDialogComponent]
            })
                .overrideTemplate(WebserviceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WebserviceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WebserviceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

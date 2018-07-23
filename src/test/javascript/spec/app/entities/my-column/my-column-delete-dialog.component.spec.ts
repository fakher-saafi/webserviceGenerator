/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { App3TestModule } from '../../../test.module';
import { MyColumnDeleteDialogComponent } from 'app/entities/my-column/my-column-delete-dialog.component';
import { MyColumnService } from 'app/entities/my-column/my-column.service';

describe('Component Tests', () => {
    describe('MyColumn Management Delete Component', () => {
        let comp: MyColumnDeleteDialogComponent;
        let fixture: ComponentFixture<MyColumnDeleteDialogComponent>;
        let service: MyColumnService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [App3TestModule],
                declarations: [MyColumnDeleteDialogComponent]
            })
                .overrideTemplate(MyColumnDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MyColumnDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MyColumnService);
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

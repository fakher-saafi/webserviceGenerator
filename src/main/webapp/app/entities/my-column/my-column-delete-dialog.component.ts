import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMyColumn } from 'app/shared/model/my-column.model';
import { MyColumnService } from './my-column.service';

@Component({
    selector: 'jhi-my-column-delete-dialog',
    templateUrl: './my-column-delete-dialog.component.html'
})
export class MyColumnDeleteDialogComponent {
    myColumn: IMyColumn;

    constructor(private myColumnService: MyColumnService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.myColumnService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'myColumnListModification',
                content: 'Deleted an myColumn'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-my-column-delete-popup',
    template: ''
})
export class MyColumnDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ myColumn }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MyColumnDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.myColumn = myColumn;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

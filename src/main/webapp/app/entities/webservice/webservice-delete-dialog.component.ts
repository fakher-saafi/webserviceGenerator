import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWebservice } from 'app/shared/model/webservice.model';
import { WebserviceService } from './webservice.service';

@Component({
    selector: 'jhi-webservice-delete-dialog',
    templateUrl: './webservice-delete-dialog.component.html'
})
export class WebserviceDeleteDialogComponent {
    webservice: IWebservice;

    constructor(private webserviceService: WebserviceService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.webserviceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'webserviceListModification',
                content: 'Deleted an webservice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-webservice-delete-popup',
    template: ''
})
export class WebserviceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ webservice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WebserviceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.webservice = webservice;
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

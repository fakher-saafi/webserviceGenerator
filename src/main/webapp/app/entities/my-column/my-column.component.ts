import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMyColumn } from 'app/shared/model/my-column.model';
import { Principal } from 'app/core';
import { MyColumnService } from './my-column.service';

@Component({
    selector: 'jhi-my-column',
    templateUrl: './my-column.component.html'
})
export class MyColumnComponent implements OnInit, OnDestroy {
    myColumns: IMyColumn[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private myColumnService: MyColumnService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.myColumnService.query().subscribe(
            (res: HttpResponse<IMyColumn[]>) => {
                this.myColumns = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMyColumns();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMyColumn) {
        return item.id;
    }

    registerChangeInMyColumns() {
        this.eventSubscriber = this.eventManager.subscribe('myColumnListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

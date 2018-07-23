import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMyTable } from 'app/shared/model/my-table.model';
import { Principal } from 'app/core';
import { MyTableService } from './my-table.service';

@Component({
    selector: 'jhi-my-table',
    templateUrl: './my-table.component.html'
})
export class MyTableComponent implements OnInit, OnDestroy {
    myTables: IMyTable[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private myTableService: MyTableService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.myTableService.query().subscribe(
            (res: HttpResponse<IMyTable[]>) => {
                this.myTables = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMyTables();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMyTable) {
        return item.id;
    }

    registerChangeInMyTables() {
        this.eventSubscriber = this.eventManager.subscribe('myTableListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

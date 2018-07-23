import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMyTable } from 'app/shared/model/my-table.model';
import { MyTableService } from './my-table.service';
import { IWebservice } from 'app/shared/model/webservice.model';
import { WebserviceService } from 'app/entities/webservice';

@Component({
    selector: 'jhi-my-table-update',
    templateUrl: './my-table-update.component.html'
})
export class MyTableUpdateComponent implements OnInit {
    private _myTable: IMyTable;
    isSaving: boolean;

    webservices: IWebservice[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private myTableService: MyTableService,
        private webserviceService: WebserviceService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ myTable }) => {
            this.myTable = myTable;
        });
        this.webserviceService.query().subscribe(
            (res: HttpResponse<IWebservice[]>) => {
                this.webservices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.myTable.id !== undefined) {
            this.subscribeToSaveResponse(this.myTableService.update(this.myTable));
        } else {
            this.subscribeToSaveResponse(this.myTableService.create(this.myTable));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMyTable>>) {
        result.subscribe((res: HttpResponse<IMyTable>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackWebserviceById(index: number, item: IWebservice) {
        return item.id;
    }
    get myTable() {
        return this._myTable;
    }

    set myTable(myTable: IMyTable) {
        this._myTable = myTable;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMyColumn } from 'app/shared/model/my-column.model';
import { MyColumnService } from './my-column.service';
import { IMyTable } from 'app/shared/model/my-table.model';
import { MyTableService } from 'app/entities/my-table';

@Component({
    selector: 'jhi-my-column-update',
    templateUrl: './my-column-update.component.html'
})
export class MyColumnUpdateComponent implements OnInit {
    private _myColumn: IMyColumn;
    isSaving: boolean;

    mytables: IMyTable[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private myColumnService: MyColumnService,
        private myTableService: MyTableService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ myColumn }) => {
            this.myColumn = myColumn;
        });
        this.myTableService.query().subscribe(
            (res: HttpResponse<IMyTable[]>) => {
                this.mytables = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.myColumn.id !== undefined) {
            this.subscribeToSaveResponse(this.myColumnService.update(this.myColumn));
        } else {
            this.subscribeToSaveResponse(this.myColumnService.create(this.myColumn));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMyColumn>>) {
        result.subscribe((res: HttpResponse<IMyColumn>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMyTableById(index: number, item: IMyTable) {
        return item.id;
    }
    get myColumn() {
        return this._myColumn;
    }

    set myColumn(myColumn: IMyColumn) {
        this._myColumn = myColumn;
    }
}

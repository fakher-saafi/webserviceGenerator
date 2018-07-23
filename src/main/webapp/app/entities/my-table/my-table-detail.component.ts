import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMyTable } from 'app/shared/model/my-table.model';

@Component({
    selector: 'jhi-my-table-detail',
    templateUrl: './my-table-detail.component.html'
})
export class MyTableDetailComponent implements OnInit {
    myTable: IMyTable;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ myTable }) => {
            this.myTable = myTable;
        });
    }

    previousState() {
        window.history.back();
    }
}

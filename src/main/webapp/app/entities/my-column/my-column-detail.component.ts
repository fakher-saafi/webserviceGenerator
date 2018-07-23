import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMyColumn } from 'app/shared/model/my-column.model';

@Component({
    selector: 'jhi-my-column-detail',
    templateUrl: './my-column-detail.component.html'
})
export class MyColumnDetailComponent implements OnInit {
    myColumn: IMyColumn;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ myColumn }) => {
            this.myColumn = myColumn;
        });
    }

    previousState() {
        window.history.back();
    }
}

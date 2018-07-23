import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWebservice } from 'app/shared/model/webservice.model';

@Component({
    selector: 'jhi-webservice-detail',
    templateUrl: './webservice-detail.component.html'
})
export class WebserviceDetailComponent implements OnInit {
    webservice: IWebservice;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ webservice }) => {
            this.webservice = webservice;
        });
    }

    previousState() {
        window.history.back();
    }
}

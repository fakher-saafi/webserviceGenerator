import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { App3SharedModule } from 'app/shared';
import { App3AdminModule } from 'app/admin/admin.module';
import {
    WebserviceComponent,
    WebserviceDetailComponent,
    WebserviceUpdateComponent,
    WebserviceDeletePopupComponent,
    WebserviceDeleteDialogComponent,
    webserviceRoute,
    webservicePopupRoute
} from './';

const ENTITY_STATES = [...webserviceRoute, ...webservicePopupRoute];

@NgModule({
    imports: [App3SharedModule, App3AdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WebserviceComponent,
        WebserviceDetailComponent,
        WebserviceUpdateComponent,
        WebserviceDeleteDialogComponent,
        WebserviceDeletePopupComponent
    ],
    entryComponents: [WebserviceComponent, WebserviceUpdateComponent, WebserviceDeleteDialogComponent, WebserviceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class App3WebserviceModule {}

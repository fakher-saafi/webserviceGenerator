import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { App3SharedModule } from 'app/shared';
import {
    MyColumnComponent,
    MyColumnDetailComponent,
    MyColumnUpdateComponent,
    MyColumnDeletePopupComponent,
    MyColumnDeleteDialogComponent,
    myColumnRoute,
    myColumnPopupRoute
} from './';

const ENTITY_STATES = [...myColumnRoute, ...myColumnPopupRoute];

@NgModule({
    imports: [App3SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MyColumnComponent,
        MyColumnDetailComponent,
        MyColumnUpdateComponent,
        MyColumnDeleteDialogComponent,
        MyColumnDeletePopupComponent
    ],
    entryComponents: [MyColumnComponent, MyColumnUpdateComponent, MyColumnDeleteDialogComponent, MyColumnDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class App3MyColumnModule {}

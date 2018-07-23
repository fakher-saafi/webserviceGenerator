import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { App3SharedModule } from 'app/shared';
import {
    MyTableComponent,
    MyTableDetailComponent,
    MyTableUpdateComponent,
    MyTableDeletePopupComponent,
    MyTableDeleteDialogComponent,
    myTableRoute,
    myTablePopupRoute
} from './';

const ENTITY_STATES = [...myTableRoute, ...myTablePopupRoute];

@NgModule({
    imports: [App3SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MyTableComponent,
        MyTableDetailComponent,
        MyTableUpdateComponent,
        MyTableDeleteDialogComponent,
        MyTableDeletePopupComponent
    ],
    entryComponents: [MyTableComponent, MyTableUpdateComponent, MyTableDeleteDialogComponent, MyTableDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class App3MyTableModule {}

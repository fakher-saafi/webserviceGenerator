import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { App3TaskModule } from './task/task.module';
import { App3WebserviceModule } from './webservice/webservice.module';
import { App3MyTableModule } from './my-table/my-table.module';
import { App3MyColumnModule } from './my-column/my-column.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        App3TaskModule,
        App3WebserviceModule,
        App3MyTableModule,
        App3MyColumnModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class App3EntityModule {}

import { NgModule } from '@angular/core';

import { App3SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [App3SharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [App3SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class App3SharedCommonModule {}

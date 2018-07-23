import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MyTable } from 'app/shared/model/my-table.model';
import { MyTableService } from './my-table.service';
import { MyTableComponent } from './my-table.component';
import { MyTableDetailComponent } from './my-table-detail.component';
import { MyTableUpdateComponent } from './my-table-update.component';
import { MyTableDeletePopupComponent } from './my-table-delete-dialog.component';
import { IMyTable } from 'app/shared/model/my-table.model';

@Injectable({ providedIn: 'root' })
export class MyTableResolve implements Resolve<IMyTable> {
    constructor(private service: MyTableService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((myTable: HttpResponse<MyTable>) => myTable.body));
        }
        return of(new MyTable());
    }
}

export const myTableRoute: Routes = [
    {
        path: 'my-table',
        component: MyTableComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MyTables'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'my-table/:id/view',
        component: MyTableDetailComponent,
        resolve: {
            myTable: MyTableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MyTables'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'my-table/new',
        component: MyTableUpdateComponent,
        resolve: {
            myTable: MyTableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MyTables'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'my-table/:id/edit',
        component: MyTableUpdateComponent,
        resolve: {
            myTable: MyTableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MyTables'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const myTablePopupRoute: Routes = [
    {
        path: 'my-table/:id/delete',
        component: MyTableDeletePopupComponent,
        resolve: {
            myTable: MyTableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MyTables'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

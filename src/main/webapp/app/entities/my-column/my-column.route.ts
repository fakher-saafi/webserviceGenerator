import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MyColumn } from 'app/shared/model/my-column.model';
import { MyColumnService } from './my-column.service';
import { MyColumnComponent } from './my-column.component';
import { MyColumnDetailComponent } from './my-column-detail.component';
import { MyColumnUpdateComponent } from './my-column-update.component';
import { MyColumnDeletePopupComponent } from './my-column-delete-dialog.component';
import { IMyColumn } from 'app/shared/model/my-column.model';

@Injectable({ providedIn: 'root' })
export class MyColumnResolve implements Resolve<IMyColumn> {
    constructor(private service: MyColumnService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((myColumn: HttpResponse<MyColumn>) => myColumn.body));
        }
        return of(new MyColumn());
    }
}

export const myColumnRoute: Routes = [
    {
        path: 'my-column',
        component: MyColumnComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MyColumns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'my-column/:id/view',
        component: MyColumnDetailComponent,
        resolve: {
            myColumn: MyColumnResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MyColumns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'my-column/new',
        component: MyColumnUpdateComponent,
        resolve: {
            myColumn: MyColumnResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MyColumns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'my-column/:id/edit',
        component: MyColumnUpdateComponent,
        resolve: {
            myColumn: MyColumnResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MyColumns'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const myColumnPopupRoute: Routes = [
    {
        path: 'my-column/:id/delete',
        component: MyColumnDeletePopupComponent,
        resolve: {
            myColumn: MyColumnResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MyColumns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMyTable } from 'app/shared/model/my-table.model';

type EntityResponseType = HttpResponse<IMyTable>;
type EntityArrayResponseType = HttpResponse<IMyTable[]>;

@Injectable({ providedIn: 'root' })
export class MyTableService {
    private resourceUrl = SERVER_API_URL + 'api/my-tables';

    constructor(private http: HttpClient) {}

    create(myTable: IMyTable): Observable<EntityResponseType> {
        return this.http.post<IMyTable>(this.resourceUrl, myTable, { observe: 'response' });
    }

    update(myTable: IMyTable): Observable<EntityResponseType> {
        return this.http.put<IMyTable>(this.resourceUrl, myTable, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMyTable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMyTable[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

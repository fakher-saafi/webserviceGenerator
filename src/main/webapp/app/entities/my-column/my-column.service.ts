import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMyColumn } from 'app/shared/model/my-column.model';

type EntityResponseType = HttpResponse<IMyColumn>;
type EntityArrayResponseType = HttpResponse<IMyColumn[]>;

@Injectable({ providedIn: 'root' })
export class MyColumnService {
    private resourceUrl = SERVER_API_URL + 'api/my-columns';

    constructor(private http: HttpClient) {}

    create(myColumn: IMyColumn): Observable<EntityResponseType> {
        return this.http.post<IMyColumn>(this.resourceUrl, myColumn, { observe: 'response' });
    }

    update(myColumn: IMyColumn): Observable<EntityResponseType> {
        return this.http.put<IMyColumn>(this.resourceUrl, myColumn, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMyColumn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMyColumn[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

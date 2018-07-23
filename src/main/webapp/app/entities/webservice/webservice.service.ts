import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWebservice } from 'app/shared/model/webservice.model';

type EntityResponseType = HttpResponse<IWebservice>;
type EntityArrayResponseType = HttpResponse<IWebservice[]>;

@Injectable({ providedIn: 'root' })
export class WebserviceService {
    private resourceUrl = SERVER_API_URL + 'api/webservices';

    constructor(private http: HttpClient) {}

    create(webservice: IWebservice): Observable<EntityResponseType> {
        return this.http.post<IWebservice>(this.resourceUrl, webservice, { observe: 'response' });
    }

    update(webservice: IWebservice): Observable<EntityResponseType> {
        return this.http.put<IWebservice>(this.resourceUrl, webservice, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IWebservice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWebservice[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    testConnection(webservice: IWebservice): Observable<HttpResponse<any>> {
        console.log(webservice.databasePath);
        return this.http.post<any>(`${this.resourceUrl}/testConnection`, webservice, { observe: 'response' });
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILit } from 'app/shared/model/lit.model';

type EntityResponseType = HttpResponse<ILit>;
type EntityArrayResponseType = HttpResponse<ILit[]>;

@Injectable({ providedIn: 'root' })
export class LitService {
    public resourceUrl = SERVER_API_URL + 'api/lits';

    constructor(private http: HttpClient) {}

    create(lit: ILit): Observable<EntityResponseType> {
        return this.http.post<ILit>(this.resourceUrl, lit, { observe: 'response' });
    }

    update(lit: ILit): Observable<EntityResponseType> {
        return this.http.put<ILit>(this.resourceUrl, lit, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILit[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

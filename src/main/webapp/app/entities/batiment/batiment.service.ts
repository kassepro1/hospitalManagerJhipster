import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBatiment } from 'app/shared/model/batiment.model';

type EntityResponseType = HttpResponse<IBatiment>;
type EntityArrayResponseType = HttpResponse<IBatiment[]>;

@Injectable({ providedIn: 'root' })
export class BatimentService {
    public resourceUrl = SERVER_API_URL + 'api/batiments';

    constructor(private http: HttpClient) {}

    create(batiment: IBatiment): Observable<EntityResponseType> {
        return this.http.post<IBatiment>(this.resourceUrl, batiment, { observe: 'response' });
    }

    update(batiment: IBatiment): Observable<EntityResponseType> {
        return this.http.put<IBatiment>(this.resourceUrl, batiment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBatiment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBatiment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

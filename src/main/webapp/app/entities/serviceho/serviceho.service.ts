import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceho } from 'app/shared/model/serviceho.model';

type EntityResponseType = HttpResponse<IServiceho>;
type EntityArrayResponseType = HttpResponse<IServiceho[]>;

@Injectable({ providedIn: 'root' })
export class ServicehoService {
    public resourceUrl = SERVER_API_URL + 'api/servicehos';

    constructor(private http: HttpClient) {}

    create(serviceho: IServiceho): Observable<EntityResponseType> {
        return this.http.post<IServiceho>(this.resourceUrl, serviceho, { observe: 'response' });
    }

    update(serviceho: IServiceho): Observable<EntityResponseType> {
        return this.http.put<IServiceho>(this.resourceUrl, serviceho, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IServiceho>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IServiceho[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

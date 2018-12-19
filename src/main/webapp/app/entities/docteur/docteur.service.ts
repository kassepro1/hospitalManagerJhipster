import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocteur } from 'app/shared/model/docteur.model';

type EntityResponseType = HttpResponse<IDocteur>;
type EntityArrayResponseType = HttpResponse<IDocteur[]>;

@Injectable({ providedIn: 'root' })
export class DocteurService {
    public resourceUrl = SERVER_API_URL + 'api/docteurs';

    constructor(private http: HttpClient) {}

    create(docteur: IDocteur): Observable<EntityResponseType> {
        return this.http.post<IDocteur>(this.resourceUrl, docteur, { observe: 'response' });
    }

    update(docteur: IDocteur): Observable<EntityResponseType> {
        return this.http.put<IDocteur>(this.resourceUrl, docteur, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDocteur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDocteur[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

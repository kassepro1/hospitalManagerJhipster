import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeTraitement } from 'app/shared/model/type-traitement.model';

type EntityResponseType = HttpResponse<ITypeTraitement>;
type EntityArrayResponseType = HttpResponse<ITypeTraitement[]>;

@Injectable({ providedIn: 'root' })
export class TypeTraitementService {
    public resourceUrl = SERVER_API_URL + 'api/type-traitements';

    constructor(private http: HttpClient) {}

    create(typeTraitement: ITypeTraitement): Observable<EntityResponseType> {
        return this.http.post<ITypeTraitement>(this.resourceUrl, typeTraitement, { observe: 'response' });
    }

    update(typeTraitement: ITypeTraitement): Observable<EntityResponseType> {
        return this.http.put<ITypeTraitement>(this.resourceUrl, typeTraitement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeTraitement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeTraitement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

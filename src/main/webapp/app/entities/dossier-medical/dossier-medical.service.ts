import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDossierMedical } from 'app/shared/model/dossier-medical.model';

type EntityResponseType = HttpResponse<IDossierMedical>;
type EntityArrayResponseType = HttpResponse<IDossierMedical[]>;

@Injectable({ providedIn: 'root' })
export class DossierMedicalService {
    public resourceUrl = SERVER_API_URL + 'api/dossier-medicals';

    constructor(private http: HttpClient) {}

    create(dossierMedical: IDossierMedical): Observable<EntityResponseType> {
        return this.http.post<IDossierMedical>(this.resourceUrl, dossierMedical, { observe: 'response' });
    }

    update(dossierMedical: IDossierMedical): Observable<EntityResponseType> {
        return this.http.put<IDossierMedical>(this.resourceUrl, dossierMedical, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDossierMedical>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDossierMedical[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

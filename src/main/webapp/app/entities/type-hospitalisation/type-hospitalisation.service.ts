import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeHospitalisation } from 'app/shared/model/type-hospitalisation.model';

type EntityResponseType = HttpResponse<ITypeHospitalisation>;
type EntityArrayResponseType = HttpResponse<ITypeHospitalisation[]>;

@Injectable({ providedIn: 'root' })
export class TypeHospitalisationService {
    public resourceUrl = SERVER_API_URL + 'api/type-hospitalisations';

    constructor(private http: HttpClient) {}

    create(typeHospitalisation: ITypeHospitalisation): Observable<EntityResponseType> {
        return this.http.post<ITypeHospitalisation>(this.resourceUrl, typeHospitalisation, { observe: 'response' });
    }

    update(typeHospitalisation: ITypeHospitalisation): Observable<EntityResponseType> {
        return this.http.put<ITypeHospitalisation>(this.resourceUrl, typeHospitalisation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeHospitalisation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeHospitalisation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

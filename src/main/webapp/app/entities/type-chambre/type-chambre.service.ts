import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeChambre } from 'app/shared/model/type-chambre.model';

type EntityResponseType = HttpResponse<ITypeChambre>;
type EntityArrayResponseType = HttpResponse<ITypeChambre[]>;

@Injectable({ providedIn: 'root' })
export class TypeChambreService {
    public resourceUrl = SERVER_API_URL + 'api/type-chambres';

    constructor(private http: HttpClient) {}

    create(typeChambre: ITypeChambre): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(typeChambre);
        return this.http
            .post<ITypeChambre>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(typeChambre: ITypeChambre): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(typeChambre);
        return this.http
            .put<ITypeChambre>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITypeChambre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITypeChambre[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(typeChambre: ITypeChambre): ITypeChambre {
        const copy: ITypeChambre = Object.assign({}, typeChambre, {
            dateInscription:
                typeChambre.dateInscription != null && typeChambre.dateInscription.isValid() ? typeChambre.dateInscription.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateInscription = res.body.dateInscription != null ? moment(res.body.dateInscription) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((typeChambre: ITypeChambre) => {
                typeChambre.dateInscription = typeChambre.dateInscription != null ? moment(typeChambre.dateInscription) : null;
            });
        }
        return res;
    }
}

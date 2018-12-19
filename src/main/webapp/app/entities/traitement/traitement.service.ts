import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITraitement } from 'app/shared/model/traitement.model';

type EntityResponseType = HttpResponse<ITraitement>;
type EntityArrayResponseType = HttpResponse<ITraitement[]>;

@Injectable({ providedIn: 'root' })
export class TraitementService {
    public resourceUrl = SERVER_API_URL + 'api/traitements';

    constructor(private http: HttpClient) {}

    create(traitement: ITraitement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(traitement);
        return this.http
            .post<ITraitement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(traitement: ITraitement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(traitement);
        return this.http
            .put<ITraitement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITraitement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITraitement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(traitement: ITraitement): ITraitement {
        const copy: ITraitement = Object.assign({}, traitement, {
            date: traitement.date != null && traitement.date.isValid() ? traitement.date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((traitement: ITraitement) => {
                traitement.date = traitement.date != null ? moment(traitement.date) : null;
            });
        }
        return res;
    }
}

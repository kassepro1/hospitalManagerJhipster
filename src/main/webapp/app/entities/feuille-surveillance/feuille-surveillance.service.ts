import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';

type EntityResponseType = HttpResponse<IFeuilleSurveillance>;
type EntityArrayResponseType = HttpResponse<IFeuilleSurveillance[]>;

@Injectable({ providedIn: 'root' })
export class FeuilleSurveillanceService {
    public resourceUrl = SERVER_API_URL + 'api/feuille-surveillances';

    constructor(private http: HttpClient) {}

    create(feuilleSurveillance: IFeuilleSurveillance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(feuilleSurveillance);
        return this.http
            .post<IFeuilleSurveillance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(feuilleSurveillance: IFeuilleSurveillance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(feuilleSurveillance);
        return this.http
            .put<IFeuilleSurveillance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFeuilleSurveillance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFeuilleSurveillance[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(feuilleSurveillance: IFeuilleSurveillance): IFeuilleSurveillance {
        const copy: IFeuilleSurveillance = Object.assign({}, feuilleSurveillance, {
            heure:
                feuilleSurveillance.heure != null && feuilleSurveillance.heure.isValid()
                    ? feuilleSurveillance.heure.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.heure = res.body.heure != null ? moment(res.body.heure) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((feuilleSurveillance: IFeuilleSurveillance) => {
                feuilleSurveillance.heure = feuilleSurveillance.heure != null ? moment(feuilleSurveillance.heure) : null;
            });
        }
        return res;
    }
}

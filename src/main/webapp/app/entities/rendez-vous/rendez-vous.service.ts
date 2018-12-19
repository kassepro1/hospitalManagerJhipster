import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRendezVous } from 'app/shared/model/rendez-vous.model';

type EntityResponseType = HttpResponse<IRendezVous>;
type EntityArrayResponseType = HttpResponse<IRendezVous[]>;

@Injectable({ providedIn: 'root' })
export class RendezVousService {
    public resourceUrl = SERVER_API_URL + 'api/rendez-vous';

    constructor(private http: HttpClient) {}

    create(rendezVous: IRendezVous): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rendezVous);
        return this.http
            .post<IRendezVous>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(rendezVous: IRendezVous): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rendezVous);
        return this.http
            .put<IRendezVous>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRendezVous>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRendezVous[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(rendezVous: IRendezVous): IRendezVous {
        const copy: IRendezVous = Object.assign({}, rendezVous, {
            dateRv: rendezVous.dateRv != null && rendezVous.dateRv.isValid() ? rendezVous.dateRv.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateRv = res.body.dateRv != null ? moment(res.body.dateRv) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((rendezVous: IRendezVous) => {
                rendezVous.dateRv = rendezVous.dateRv != null ? moment(rendezVous.dateRv) : null;
            });
        }
        return res;
    }
}

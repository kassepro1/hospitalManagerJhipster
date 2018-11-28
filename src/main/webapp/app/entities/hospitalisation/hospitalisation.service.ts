import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHospitalisation } from 'app/shared/model/hospitalisation.model';

type EntityResponseType = HttpResponse<IHospitalisation>;
type EntityArrayResponseType = HttpResponse<IHospitalisation[]>;

@Injectable({ providedIn: 'root' })
export class HospitalisationService {
    public resourceUrl = SERVER_API_URL + 'api/hospitalisations';

    constructor(private http: HttpClient) {}

    create(hospitalisation: IHospitalisation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(hospitalisation);
        return this.http
            .post<IHospitalisation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(hospitalisation: IHospitalisation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(hospitalisation);
        return this.http
            .put<IHospitalisation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHospitalisation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHospitalisation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(hospitalisation: IHospitalisation): IHospitalisation {
        const copy: IHospitalisation = Object.assign({}, hospitalisation, {
            dateEntree:
                hospitalisation.dateEntree != null && hospitalisation.dateEntree.isValid()
                    ? hospitalisation.dateEntree.format(DATE_FORMAT)
                    : null,
            dateSortie:
                hospitalisation.dateSortie != null && hospitalisation.dateSortie.isValid()
                    ? hospitalisation.dateSortie.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateEntree = res.body.dateEntree != null ? moment(res.body.dateEntree) : null;
            res.body.dateSortie = res.body.dateSortie != null ? moment(res.body.dateSortie) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((hospitalisation: IHospitalisation) => {
                hospitalisation.dateEntree = hospitalisation.dateEntree != null ? moment(hospitalisation.dateEntree) : null;
                hospitalisation.dateSortie = hospitalisation.dateSortie != null ? moment(hospitalisation.dateSortie) : null;
            });
        }
        return res;
    }
}

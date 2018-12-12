import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPatient } from 'app/shared/model/patient.model';

type EntityResponseType = HttpResponse<IPatient>;
type EntityArrayResponseType = HttpResponse<IPatient[]>;

@Injectable({ providedIn: 'root' })
export class PatientService {
    public resourceUrl = SERVER_API_URL + 'api/patients';

    constructor(private http: HttpClient) {}

    create(patient: IPatient): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(patient);
        return this.http
            .post<IPatient>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(patient: IPatient): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(patient);
        return this.http
            .put<IPatient>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPatient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPatient[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(patient: IPatient): IPatient {
        const copy: IPatient = Object.assign({}, patient, {
            datenaiss: patient.datenaiss != null && patient.datenaiss.isValid() ? patient.datenaiss.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.datenaiss = res.body.datenaiss != null ? moment(res.body.datenaiss) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((patient: IPatient) => {
                patient.datenaiss = patient.datenaiss != null ? moment(patient.datenaiss) : null;
            });
        }
        return res;
    }
}

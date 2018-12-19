/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FeuilleSurveillanceService } from 'app/entities/feuille-surveillance/feuille-surveillance.service';
import { IFeuilleSurveillance, FeuilleSurveillance } from 'app/shared/model/feuille-surveillance.model';

describe('Service Tests', () => {
    describe('FeuilleSurveillance Service', () => {
        let injector: TestBed;
        let service: FeuilleSurveillanceService;
        let httpMock: HttpTestingController;
        let elemDefault: IFeuilleSurveillance;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(FeuilleSurveillanceService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new FeuilleSurveillance(
                0,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        heure: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a FeuilleSurveillance', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        heure: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        heure: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new FeuilleSurveillance(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a FeuilleSurveillance', async () => {
                const returnedFromService = Object.assign(
                    {
                        heure: currentDate.format(DATE_FORMAT),
                        pouls: 'BBBBBB',
                        pa: 'BBBBBB',
                        temperature: 'BBBBBB',
                        freqRespi: 'BBBBBB',
                        diurese: 'BBBBBB',
                        globeUterin: 'BBBBBB',
                        observation: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        heure: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of FeuilleSurveillance', async () => {
                const returnedFromService = Object.assign(
                    {
                        heure: currentDate.format(DATE_FORMAT),
                        pouls: 'BBBBBB',
                        pa: 'BBBBBB',
                        temperature: 'BBBBBB',
                        freqRespi: 'BBBBBB',
                        diurese: 'BBBBBB',
                        globeUterin: 'BBBBBB',
                        observation: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        heure: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a FeuilleSurveillance', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});

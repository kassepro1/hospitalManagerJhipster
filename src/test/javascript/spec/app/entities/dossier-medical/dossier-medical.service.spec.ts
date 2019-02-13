/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { DossierMedicalService } from 'app/entities/dossier-medical/dossier-medical.service';
import { IDossierMedical, DossierMedical } from 'app/shared/model/dossier-medical.model';

describe('Service Tests', () => {
    describe('DossierMedical Service', () => {
        let injector: TestBed;
        let service: DossierMedicalService;
        let httpMock: HttpTestingController;
        let elemDefault: IDossierMedical;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(DossierMedicalService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new DossierMedical(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a DossierMedical', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new DossierMedical(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a DossierMedical', async () => {
                const returnedFromService = Object.assign(
                    {
                        nom: 'BBBBBB',
                        prenom: 'BBBBBB',
                        numFiche: 'BBBBBB',
                        taille: 1,
                        poids: 1,
                        tension: 'BBBBBB',
                        temperature: 'BBBBBB',
                        photo: 'BBBBBB',
                        resultat: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of DossierMedical', async () => {
                const returnedFromService = Object.assign(
                    {
                        nom: 'BBBBBB',
                        prenom: 'BBBBBB',
                        numFiche: 'BBBBBB',
                        taille: 1,
                        poids: 1,
                        tension: 'BBBBBB',
                        temperature: 'BBBBBB',
                        photo: 'BBBBBB',
                        resultat: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a DossierMedical', async () => {
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

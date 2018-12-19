import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceho } from 'app/shared/model/serviceho.model';

@Component({
    selector: 'jhi-serviceho-detail',
    templateUrl: './serviceho-detail.component.html'
})
export class ServicehoDetailComponent implements OnInit {
    serviceho: IServiceho;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ serviceho }) => {
            this.serviceho = serviceho;
        });
    }

    previousState() {
        window.history.back();
    }
}

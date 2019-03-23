import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExaminationReading } from 'app/shared/model/examination-reading.model';
import { ExaminationReadingService } from './examination-reading.service';
import { ExaminationReadingComponent } from './examination-reading.component';
import { ExaminationReadingDetailComponent } from './examination-reading-detail.component';
import { ExaminationReadingUpdateComponent } from './examination-reading-update.component';
import { ExaminationReadingDeletePopupComponent } from './examination-reading-delete-dialog.component';
import { IExaminationReading } from 'app/shared/model/examination-reading.model';

@Injectable({ providedIn: 'root' })
export class ExaminationReadingResolve implements Resolve<IExaminationReading> {
    constructor(private service: ExaminationReadingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExaminationReading> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExaminationReading>) => response.ok),
                map((examinationReading: HttpResponse<ExaminationReading>) => examinationReading.body)
            );
        }
        return of(new ExaminationReading());
    }
}

export const examinationReadingRoute: Routes = [
    {
        path: '',
        component: ExaminationReadingComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ExaminationReadings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExaminationReadingDetailComponent,
        resolve: {
            examinationReading: ExaminationReadingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExaminationReadings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExaminationReadingUpdateComponent,
        resolve: {
            examinationReading: ExaminationReadingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExaminationReadings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExaminationReadingUpdateComponent,
        resolve: {
            examinationReading: ExaminationReadingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExaminationReadings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const examinationReadingPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExaminationReadingDeletePopupComponent,
        resolve: {
            examinationReading: ExaminationReadingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExaminationReadings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

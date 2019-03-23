import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Expert } from 'app/shared/model/expert.model';
import { ExpertService } from './expert.service';
import { ExpertComponent } from './expert.component';
import { ExpertDetailComponent } from './expert-detail.component';
import { ExpertUpdateComponent } from './expert-update.component';
import { ExpertDeletePopupComponent } from './expert-delete-dialog.component';
import { IExpert } from 'app/shared/model/expert.model';

@Injectable({ providedIn: 'root' })
export class ExpertResolve implements Resolve<IExpert> {
    constructor(private service: ExpertService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExpert> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Expert>) => response.ok),
                map((expert: HttpResponse<Expert>) => expert.body)
            );
        }
        return of(new Expert());
    }
}

export const expertRoute: Routes = [
    {
        path: '',
        component: ExpertComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Experts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExpertDetailComponent,
        resolve: {
            expert: ExpertResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Experts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExpertUpdateComponent,
        resolve: {
            expert: ExpertResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Experts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExpertUpdateComponent,
        resolve: {
            expert: ExpertResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Experts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const expertPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExpertDeletePopupComponent,
        resolve: {
            expert: ExpertResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Experts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

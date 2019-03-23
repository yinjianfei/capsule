import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Capsule } from 'app/shared/model/capsule.model';
import { CapsuleService } from './capsule.service';
import { CapsuleComponent } from './capsule.component';
import { CapsuleDetailComponent } from './capsule-detail.component';
import { CapsuleUpdateComponent } from './capsule-update.component';
import { CapsuleDeletePopupComponent } from './capsule-delete-dialog.component';
import { ICapsule } from 'app/shared/model/capsule.model';

@Injectable({ providedIn: 'root' })
export class CapsuleResolve implements Resolve<ICapsule> {
    constructor(private service: CapsuleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICapsule> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Capsule>) => response.ok),
                map((capsule: HttpResponse<Capsule>) => capsule.body)
            );
        }
        return of(new Capsule());
    }
}

export const capsuleRoute: Routes = [
    {
        path: '',
        component: CapsuleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Capsules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CapsuleDetailComponent,
        resolve: {
            capsule: CapsuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Capsules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CapsuleUpdateComponent,
        resolve: {
            capsule: CapsuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Capsules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CapsuleUpdateComponent,
        resolve: {
            capsule: CapsuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Capsules'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const capsulePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CapsuleDeletePopupComponent,
        resolve: {
            capsule: CapsuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Capsules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

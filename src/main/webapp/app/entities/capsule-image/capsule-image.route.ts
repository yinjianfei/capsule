import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CapsuleImage } from 'app/shared/model/capsule-image.model';
import { CapsuleImageService } from './capsule-image.service';
import { CapsuleImageComponent } from './capsule-image.component';
import { CapsuleImageDetailComponent } from './capsule-image-detail.component';
import { CapsuleImageUpdateComponent } from './capsule-image-update.component';
import { CapsuleImageDeletePopupComponent } from './capsule-image-delete-dialog.component';
import { ICapsuleImage } from 'app/shared/model/capsule-image.model';

@Injectable({ providedIn: 'root' })
export class CapsuleImageResolve implements Resolve<ICapsuleImage> {
    constructor(private service: CapsuleImageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICapsuleImage> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CapsuleImage>) => response.ok),
                map((capsuleImage: HttpResponse<CapsuleImage>) => capsuleImage.body)
            );
        }
        return of(new CapsuleImage());
    }
}

export const capsuleImageRoute: Routes = [
    {
        path: '',
        component: CapsuleImageComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CapsuleImages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CapsuleImageDetailComponent,
        resolve: {
            capsuleImage: CapsuleImageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CapsuleImages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CapsuleImageUpdateComponent,
        resolve: {
            capsuleImage: CapsuleImageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CapsuleImages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CapsuleImageUpdateComponent,
        resolve: {
            capsuleImage: CapsuleImageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CapsuleImages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const capsuleImagePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CapsuleImageDeletePopupComponent,
        resolve: {
            capsuleImage: CapsuleImageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CapsuleImages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

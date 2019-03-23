import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICapsuleImage } from 'app/shared/model/capsule-image.model';

type EntityResponseType = HttpResponse<ICapsuleImage>;
type EntityArrayResponseType = HttpResponse<ICapsuleImage[]>;

@Injectable({ providedIn: 'root' })
export class CapsuleImageService {
    public resourceUrl = SERVER_API_URL + 'api/capsule-images';

    constructor(protected http: HttpClient) {}

    create(capsuleImage: ICapsuleImage): Observable<EntityResponseType> {
        return this.http.post<ICapsuleImage>(this.resourceUrl, capsuleImage, { observe: 'response' });
    }

    update(capsuleImage: ICapsuleImage): Observable<EntityResponseType> {
        return this.http.put<ICapsuleImage>(this.resourceUrl, capsuleImage, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICapsuleImage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICapsuleImage[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

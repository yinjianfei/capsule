import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICapsule } from 'app/shared/model/capsule.model';

type EntityResponseType = HttpResponse<ICapsule>;
type EntityArrayResponseType = HttpResponse<ICapsule[]>;

@Injectable({ providedIn: 'root' })
export class CapsuleService {
    public resourceUrl = SERVER_API_URL + 'api/capsules';

    constructor(protected http: HttpClient) {}

    create(capsule: ICapsule): Observable<EntityResponseType> {
        return this.http.post<ICapsule>(this.resourceUrl, capsule, { observe: 'response' });
    }

    update(capsule: ICapsule): Observable<EntityResponseType> {
        return this.http.put<ICapsule>(this.resourceUrl, capsule, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICapsule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICapsule[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

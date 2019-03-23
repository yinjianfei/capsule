import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExpert } from 'app/shared/model/expert.model';

type EntityResponseType = HttpResponse<IExpert>;
type EntityArrayResponseType = HttpResponse<IExpert[]>;

@Injectable({ providedIn: 'root' })
export class ExpertService {
    public resourceUrl = SERVER_API_URL + 'api/experts';

    constructor(protected http: HttpClient) {}

    create(expert: IExpert): Observable<EntityResponseType> {
        return this.http.post<IExpert>(this.resourceUrl, expert, { observe: 'response' });
    }

    update(expert: IExpert): Observable<EntityResponseType> {
        return this.http.put<IExpert>(this.resourceUrl, expert, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExpert>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExpert[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

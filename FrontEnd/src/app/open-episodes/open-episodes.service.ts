import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class OpenEpisodesService {

  constructor(private http: Http) { }

  public getConfig(): Observable<any> {
    return this.http.get('api/config', {withCredentials: true})
      .map((response) => response.json());
  }
}

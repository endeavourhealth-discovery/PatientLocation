import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {OngoingEncounter} from '../models/OngoingEncounter';

@Injectable()
export class OpenEpisodesService {

  constructor(private http: Http) { }

  public getOngoingEncounters(): Observable<any[]> {
    return this.http.get('api/encounters/ongoing', {withCredentials: true})
      .map((response) => response.json());
  }
}

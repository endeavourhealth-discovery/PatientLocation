import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {OpenEpisode} from '../models/OpenEpisode';

@Injectable()
export class OpenEpisodesService {

  constructor(private http: Http) { }

  public getInpatients(): Observable<any[]> {
    return this.http.get('api/episode/inpatient', {withCredentials: true})
      .map((response) => response.json());
  }

  public getOutpatients(): Observable<any[]> {
    return this.http.get('api/episode/outpatient', {withCredentials: true})
      .map((response) => response.json());
  }

  public getEmergencies(): Observable<any[]> {
    return this.http.get('api/episode/emergency', {withCredentials: true})
      .map((response) => response.json());
  }
}

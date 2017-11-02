import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {OpenEpisode} from '../models/OpenEpisode';

@Injectable()
export class OpenEpisodesService {

  constructor(private http: Http) { }

  public getOpenEpisodes(): Observable<OpenEpisode[]> {
    return this.http.get('api/episode/open', {withCredentials: true})
      .map((response) => response.json());
  }
}

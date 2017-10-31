import {Injectable} from '@angular/core';
import {MenuOption} from 'eds-angular4/dist/layout/models/MenuOption';
import {AbstractMenuProvider} from 'eds-angular4';
import {Routes} from '@angular/router';
import {OpenEpisodesComponent} from './open-episodes/open-episodes.component';

@Injectable()
export class AppMenuService implements  AbstractMenuProvider {
  static getRoutes(): Routes {
    return [
      { path: '', redirectTo : 'open-episodes', pathMatch: 'full' },
      { path: 'open-episodes', component: OpenEpisodesComponent }
    ]
  }

  getClientId(): string {
    return 'patient-location';
  }
  getApplicationTitle(): string {
    return 'Patient Location';
  }
  getMenuOptions(): MenuOption[] {
    return [
      {caption: 'Open episodes', state: 'open-episodes', icon: 'fa fa-map-marker', role: 'patient-location:open-episodes'},
    ];
  }
}

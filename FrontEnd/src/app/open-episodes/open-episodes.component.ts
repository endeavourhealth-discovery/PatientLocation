import {Component, OnInit} from '@angular/core';
import {OpenEpisodesService} from './open-episodes.service';
import {LoggerService, SecurityService} from 'eds-angular4';

@Component({
  selector: 'app-open-episodes-component',
  templateUrl: './open-episodes.html',
  styleUrls: ['./open-episodes.component.css']
})
export class OpenEpisodesComponent implements OnInit {

  constructor(protected logger: LoggerService,
              protected security: SecurityService,
              protected service: OpenEpisodesService) {
  }

  ngOnInit() {
    this.loadOpenEpisodes();
  }

  public getUserOrg(): string {
    return this.security.currentUser.organisation;
  }

  private loadOpenEpisodes(): void {
    this.service.getOpenEpisodes()
      .subscribe(
        (result) => console.log(result),
        (error) => console.error(error)
      );
  }
}

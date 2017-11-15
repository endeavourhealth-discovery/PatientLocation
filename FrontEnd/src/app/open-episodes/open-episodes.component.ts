import {Component, OnInit} from '@angular/core';
import {OpenEpisodesService} from './open-episodes.service';
import {LoggerService, SecurityService} from 'eds-angular4';

@Component({
  selector: 'app-open-episodes-component',
  templateUrl: './open-episodes.html',
  styleUrls: ['./open-episodes.component.css']
})
export class OpenEpisodesComponent implements OnInit {
  private inpatients: any[];
  private outpatients: any[];
  private emergency: any[];


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
    const vm = this;

    this.service.getInpatients()
      .subscribe(
        (result) => vm.inpatients = result,
        (error) => vm.logger.error(error)
      );

    this.service.getOutpatients()
      .subscribe(
        (result) => vm.outpatients = result,
        (error) => vm.logger.error(error)
      );

    this.service.getEmergencies()
      .subscribe(
        (result) => vm.emergency = result,
        (error) => vm.logger.error(error)
      );

    // this.service.getOpenEpisodes()
    //   .subscribe(
    //     (result) => console.log(result),
    //     (error) => console.error(error)
    //   );
  }
}

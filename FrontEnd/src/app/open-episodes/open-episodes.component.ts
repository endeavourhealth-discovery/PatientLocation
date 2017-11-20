import {Component, OnInit} from '@angular/core';
import {OpenEpisodesService} from './open-episodes.service';
import {LoggerService, SecurityService} from 'eds-angular4';
import {OngoingEncounter} from '../models/OngoingEncounter';

@Component({
  selector: 'app-open-episodes-component',
  templateUrl: './open-episodes.html',
  styleUrls: ['./open-episodes.component.css']
})
export class OpenEpisodesComponent implements OnInit {
  private ongoingEncountersByStatus: Map<number, OngoingEncounter[]>;
  private statusList: number[];

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
    vm.ongoingEncountersByStatus = null;
    this.service.getOngoingEncounters()
      .subscribe(
        (result) => vm.processEncounters(result),
        (error) => vm.logger.error(error)
      );
  }

  private processEncounters(ongoingEncouters: OngoingEncounter[]) {
    const tempMap = new Map();
    const tempStatusList = [];

    for(const ongoingEncounter of ongoingEncouters) {
      if (!tempMap.has(ongoingEncounter.status)) {
        tempStatusList.push(ongoingEncounter.status);
        tempMap.set(ongoingEncounter.status, []);
      }

      tempMap.get(ongoingEncounter.status).push(ongoingEncounter);
    }

    this.ongoingEncountersByStatus = tempMap;
    this.statusList = tempStatusList;
  }

  private getStatusName(status: number) {
    switch(status) {
      case 8023: return 'Inpatient';
      case 8018: return 'Outpatient';
      case 8024: return 'Day case';
      case 8048: return 'Emergency';
      case 8049: return 'Waiting list';
      default: return 'Unknown';
    }
  }

  private getEncountersByStatus(status: number): OngoingEncounter[] {
    return this.ongoingEncountersByStatus.get(status);
  }
}

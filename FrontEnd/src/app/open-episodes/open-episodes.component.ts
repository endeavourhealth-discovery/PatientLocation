import {Component, OnInit} from '@angular/core';
import {OpenEpisodesService} from './open-episodes.service';
import {LoggerService} from 'eds-angular4';

@Component({
  selector: 'app-open-episodes-component',
  templateUrl: './open-episodes.html',
  styleUrls: ['./open-episodes.component.css']
})
export class OpenEpisodesComponent implements OnInit {

  constructor(protected logger: LoggerService,
              protected service: OpenEpisodesService) {
  }

  ngOnInit() {
  }
}

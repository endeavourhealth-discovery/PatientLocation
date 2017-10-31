import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MockSecurityService} from './mock.security.service';
import {MockOpenEpisodesService} from './mock.open-episodes.service';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [],
  providers : [
    MockSecurityService,
    MockOpenEpisodesService
  ]
})
export class MocksModule { }

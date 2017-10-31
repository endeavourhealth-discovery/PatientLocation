import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenEpisodesComponent } from './open-episodes.component';
import {MockSecurityService} from '../mocks/mock.security.service';
import {ControlsModule, LoggerModule, SecurityService} from 'eds-angular4';
import {OpenEpisodesService} from './open-episodes.service';
import {MockOpenEpisodesService} from '../mocks/mock.open-episodes.service';
import {FormsModule} from '@angular/forms';
import {ToastModule} from 'ng2-toastr/ng2-toastr';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

describe('OpenEpisodesComponent', () => {
  let component: OpenEpisodesComponent;
  let fixture: ComponentFixture<OpenEpisodesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, ControlsModule, LoggerModule, ToastModule.forRoot(), NgbModule.forRoot()],
      declarations: [ OpenEpisodesComponent ],
      providers: [
        {provide : SecurityService, useClass: MockSecurityService },
        {provide : OpenEpisodesService, useClass: MockOpenEpisodesService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenEpisodesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});

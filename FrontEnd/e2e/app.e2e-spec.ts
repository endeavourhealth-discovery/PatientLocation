import { AngularPage } from './app.po';
import {$, browser, by, element} from 'protractor';

describe('angular App', () => {
  let page: AngularPage;

  beforeEach(() => {
    page = new AngularPage();
  });

  it ('Initialize', () => {
    page.navigateTo();

    // Wait for login.
    browser.wait(browser.ExpectedConditions.urlContains('/auth/realms/endeavour/protocol/openid-connect'));
  });

  it ('Perform login', () => {
    element(by.name('username')).sendKeys('e2etest');
    element(by.name('password')).sendKeys('e2eTestPass');
    element(by.name('login')).click();

    // Wait for main app page
    browser.wait(() => $('#content').isPresent());
  });

  it ('Check page loaded', done => {
    element(by.className('title-text')).getText()
      .then(msg => expect(msg).toEqual('Data Validation'))
      .then(done, done.fail);
  });
});

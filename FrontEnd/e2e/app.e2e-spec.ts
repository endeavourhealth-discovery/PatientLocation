import { Application } from './app.po';
import {$, browser, by, element} from 'protractor';

describe('angular App', () => {
  let app: Application;

  beforeEach(() => {
    app = new Application();
  });

  it ('Initialize', () => {
    app.navigateTo();

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
    expect(element(by.className('title-text')).getText()).toBe('Data Validation');
  });
});

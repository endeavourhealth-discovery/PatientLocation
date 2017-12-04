import {browser, by, element, protractor} from 'protractor';

export class Application {
  navigateTo() {
    browser.waitForAngularEnabled(false);
    browser.get('/');
  }
}


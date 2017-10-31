import {browser, by, element, protractor} from 'protractor';

export class AngularPage {
  navigateTo() {
    browser.waitForAngularEnabled(false);
    browser.get('/');
  }
}


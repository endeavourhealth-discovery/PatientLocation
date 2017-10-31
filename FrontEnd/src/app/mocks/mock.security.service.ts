import { Injectable } from '@angular/core';
import {User} from 'eds-angular4/dist/security/models/User';
import {Access} from 'eds-angular4/dist/security/models/Access';

@Injectable()
export class MockSecurityService {

  public currentUser: User;

  constructor() {
    const clientAccessMap: any = {'eds-compass': {roles: ['eds-compass:dashboard']} as Access};

    this.currentUser = {
      title : 'Mr',
      forename : 'Mock',
      surname : 'User',
      clientAccess : clientAccessMap
    } as User;
  }

  getCurrentUser(): User {
    return this.currentUser;
  }

}

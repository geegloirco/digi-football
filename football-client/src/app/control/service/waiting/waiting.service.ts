import { Injectable } from '@angular/core';
import {Subject} from "rxjs/Subject";
import {Observable} from "rxjs/Observable";

@Injectable()
export class WaitingService {
  isWaited: boolean = false;
  subject = new Subject();

  constructor() {
    console.log("WaitingService.constructor()");
  }

  getIsWaited() {
    return this.isWaited;
  }

  doWait() {
    this.isWaited = true;
    this.subject.next(this.isWaited);
  }

  endWait() {
    this.isWaited = false;
    this.subject.next(this.isWaited);
  }

  subscribeWaiting(): Observable<any> {
    console.log("MessageService.getMessages()");
    return this.subject.asObservable();
  }
}

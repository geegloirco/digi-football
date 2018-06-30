import { Injectable } from '@angular/core';
import {Subject} from "rxjs/Subject";
import {Observable} from "rxjs/Observable";
import {IntervalObservable} from "rxjs/observable/IntervalObservable";

@Injectable()
export class MessageService {
  messages: object[] = [];
  subject = new Subject();

  constructor() {
    console.log("MessageService.constructor()");

    IntervalObservable.create(3000).subscribe(() => {
      let current = Date.now();
      let newMessages = [];
      for(let i = 0; i < this.messages.length; i++) {
        if(current - this.messages[i]['time'] < 10000)
          newMessages.push(this.messages[i]);
      }
      this.messages = newMessages;
      this.subject.next(this.messages);
    });
  }

  getMessages() {
    return this.messages;
  }

  subscribeMessages(): Observable<any> {
    console.log("MessageService.getMessages()");
    return this.subject.asObservable();
  }

  add(message: string) {
    console.log("MessageService.add()");
    this.messages.push({message: message, time: Date.now()});
    this.subject.next(this.messages);
  }

  remove(message: object) {
    console.log("MessageService.remove()");
    let index: number = this.messages.indexOf(message);
    console.log(index);
    if (index !== -1) {
      this.messages.splice(index, 1);
      this.subject.next(this.messages);
    }
  }

  clear() {
    this.messages = [];
    this.subject.next(this.messages);
  }
}

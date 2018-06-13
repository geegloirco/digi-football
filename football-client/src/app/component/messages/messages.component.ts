import { Component, OnInit } from '@angular/core';

import { MessageService } from '../../service/message/message.service';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit {
  messages = null;

  constructor(public messageService: MessageService) {
    console.log("MessagesComponent.constructor()");
  }

  ngOnInit() {
    console.log("MessagesComponent.ngOnInit()")
    this.messages = this.messageService.getMessages();
    this.messageService.subscribeMessages().subscribe(res => {
      this.messages = res;
    });
  }

}

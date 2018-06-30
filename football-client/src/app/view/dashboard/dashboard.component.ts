import {
  AfterContentInit,
  AfterViewChecked,
  AfterViewInit, Component, ElementRef, OnInit,
  ViewChild
} from '@angular/core';
import {MessagesComponent} from "../../control/component/messages/messages.component";
import {MessageService} from "../../control/service/message/message.service";
import {WaitingService} from "../../control/service/waiting/waiting.service";
import {WindowSizeService} from "../../basic/service/window-size.service";
import {WindowSizeModel} from "../../basic/model/window-size.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, AfterViewInit, AfterContentInit {
  windowSizeModel: WindowSizeModel;
  isSmallMode = false;
  isWaited = true;

  itsSize = 0;

  constructor(
    public waitingService: WaitingService,
    public messageService: MessageService,
    private windowSizeService: WindowSizeService) {
    this.windowSizeModel = this.windowSizeService.getSize();
  }

  ngOnInit() {
    console.log("DashboardComponent ngOnInit");
    this.isWaited = this.waitingService.getIsWaited();
    this.waitingService.subscribeWaiting().subscribe(isWaited => {
      this.isWaited = isWaited;
    });
  }

  ngAfterViewInit() {
    console.log("DashboardComponent ngAfterViewInit");
    this.windowSizeService.changeSize().subscribe(size => {
      this.windowSizeModel = size;
    });
  }

  ngAfterContentInit() {
    console.log("DashboardComponent ngAfterContentInit");
  }

  windowResized(isSmall: boolean, size, owner: DashboardComponent) {
    console.log("dashboard windowResized");
    owner.itsSize = size;
    owner.isSmallMode = isSmall;
  }
}

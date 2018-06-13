import {
  AfterViewInit, Component, ElementRef, OnInit,
  ViewChild
} from '@angular/core';
import {RootContainerService} from "../../control/root-container/root-container.component";
import {MessagesComponent} from "../messages/messages.component";
import {MessageService} from "../../service/message/message.service";
import {WaitingService} from "../../service/waiting/waiting.service";
import {WindowSizeService} from "../../basic/service/window-size.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, AfterViewInit {
  isSmallMode = false;

  @ViewChild('body') other: ElementRef;
  windowSizeModel: WindowSizeModel;

  constructor(
    public waitingService: WaitingService,
    public messageService: MessageService,
    private windowSizeService: WindowSizeService,
    private element: ElementRef) {
    this.windowSizeModel = this.windowSizeService.getSize();
    this.windowSizeService.changeSize().subscribe(size => {
      this.windowSizeModel = size;
      this.menuState = false;
      this.myDrop.close();
    });
  }

  isWaited = false;

  ngOnInit() {
    this.waitingService.subscribeWaiting().subscribe(isWaited => {
      this.isWaited = isWaited;
    });
  }

  ngAfterViewInit() {
    // console.log(this.element.nativeElement.clientHeight);
    // console.log(this.header);
    // console.log(this.other);
    // let headerHeight = this.body.nativeElement.clientHeight;

  }

  itsSize = 0;

  windowResized(isSmall: boolean, size, owner: DashboardComponent) {
    console.log("dashboard windowResized");
    owner.itsSize = size;
    owner.isSmallMode = isSmall;
  }
}

import {Component, HostListener, OnInit} from '@angular/core';
import { WindowSizeService} from "../../service/window-size.service";

@Component({
  selector: 'basic-window-container',
  templateUrl: './basic-window-container.component.html',
  styleUrls: ['./basic-window-container.component.css']
})
export class BasicWindowContainerComponent implements OnInit {
  constructor(
    private windowSizeService: WindowSizeService) {
  }

  ngOnInit() {
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    // console.log("onResize")
    this.windowSizeService.setWindowSizeModel(
      event.target.innerWidth,
      event.target.innerHeight);
  }
}

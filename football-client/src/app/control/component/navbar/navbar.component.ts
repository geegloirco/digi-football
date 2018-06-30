import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { WindowSizeService } from "../../../basic/service/window-size.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  uploadBtn = false;
  configBtn = true;
  operationBtn = false;

  constructor(
    private windowSizeService: WindowSizeService,
    private activateRoute: ActivatedRoute) { }

  ngOnInit() {
    console.log("NavbarComponent ngOnInit");
    if (this.activateRoute.snapshot.firstChild) {
      let path = this.activateRoute.snapshot.firstChild.url[0].path;
    }
  }
}

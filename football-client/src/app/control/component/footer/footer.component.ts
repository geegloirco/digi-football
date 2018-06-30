import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { WindowSizeService } from "../../../basic/service/window-size.service";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  constructor(
    private windowSizeService: WindowSizeService,
    private activateRoute: ActivatedRoute) { }

  ngOnInit() {
    console.log("FooterComponent ngOnInit")
  }
}

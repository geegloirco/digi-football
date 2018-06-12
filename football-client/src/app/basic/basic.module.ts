import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { WindowRefService } from './service/win-ref.service';
import {WindowSizeService} from "./service/window-size.service";
import {BasicWindowContainerComponent} from "./component/basic-window-container/basic-window-container.component";
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "../app-routing.module";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
  ],
  declarations: [
    BasicWindowContainerComponent,
  ],
  providers: [
    WindowRefService,
    WindowSizeService,
  ],
  exports: [
    BasicWindowContainerComponent,
  ]
})
export class BasicModule { }

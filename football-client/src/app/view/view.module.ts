import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {WindowRefService} from './service/win-ref.service';
import {WindowSizeService} from "./service/window-size.service";
import {BasicWindowContainerComponent} from "./component/basic-window-container/basic-window-container.component";
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "../app-routing.module";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {ComponentModule} from "../component/component.module";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    ComponentModule,
  ],
  declarations: [
    DashboardComponent,
  ],
  providers: [
  ],
  exports: [
    DashboardComponent,
  ]
})
export class ViewModule { }

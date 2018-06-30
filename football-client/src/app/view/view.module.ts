import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "../app-routing.module";
import {DashboardComponent} from "../view/dashboard/dashboard.component";
import {ControlModule} from "../control/control.module";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    ControlModule,
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

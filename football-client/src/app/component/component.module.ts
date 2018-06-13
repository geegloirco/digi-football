import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {WindowRefService} from './service/win-ref.service';
import {WindowSizeService} from "./service/window-size.service";
import {BasicWindowContainerComponent} from "./component/basic-window-container/basic-window-container.component";
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "../app-routing.module";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {LoadWaitComponent} from "./load-wait/load-wait.component";
import {MessagesComponent} from "./messages/messages.component";
import {LoginComponent} from "./login/login.component";
import {NavbarComponent} from "./navbar/navbar.component";
import {MessageService} from "../service/message/message.service";
import {WaitingService} from "../service/waiting/waiting.service";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    NgbModule,
    AppRoutingModule,
  ],
  declarations: [
    NavbarComponent,
    LoginComponent,
    MessagesComponent,
    LoadWaitComponent,
  ],
  providers: [
    MessageService,
    WaitingService,
  ],
  exports: [
    NavbarComponent,
    LoginComponent,
    MessagesComponent,
    LoadWaitComponent,
  ],
})
export class ComponentModule { }

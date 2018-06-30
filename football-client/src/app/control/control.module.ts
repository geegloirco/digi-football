import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "../app-routing.module";
import {LoadWaitComponent} from "./component/load-wait/load-wait.component";
import {MessagesComponent} from "./component/messages/messages.component";
import {LoginComponent} from "./component/login/login.component";
import {NavbarComponent} from "./component/navbar/navbar.component";
import {MessageService} from "./service/message/message.service";
import {WaitingService} from "./service/waiting/waiting.service";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {BasicModule} from "../basic/basic.module";
import {FooterComponent} from "./component/footer/footer.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    NgbModule,
    BasicModule,
    AppRoutingModule,
  ],
  declarations: [
    NavbarComponent,
    FooterComponent,
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
    FooterComponent,
    LoginComponent,
    MessagesComponent,
    LoadWaitComponent,
  ],
})
export class ControlModule { }

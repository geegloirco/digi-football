import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BasicModule } from "./basic/basic.module";
import {AppRoutingModule} from "./app-routing.module";
import { BasicWindowContainerComponent } from "./basic/component/basic-window-container/basic-window-container.component";
import { NavbarComponent } from "./component/navbar/navbar.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {GoogleAuthService} from "./service/google-auth/google-auth.service";
import {ServerInfoService} from "./service/server-info/server-info.service";
import {LoginComponent} from "./component/login/login.component";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CustomInterceptor} from "./service/custom-browser-xhr/custom-browser-xhr.service";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {MessageService} from "./service/message/message.service";
import {MessagesComponent} from "./component/messages/messages.component";
import {LoadWaitComponent} from "./component/load-wait/load-wait.component";
import {DashboardComponent} from "./view/dashboard/dashboard.component";
import {WaitingService} from "./service/waiting/waiting.service";
import {ViewModule} from "./view/view.module";
import {ComponentModule} from "./component/component.module";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule.forRoot(),
    BasicModule,
    ComponentModule,
    ViewModule,
    AppRoutingModule,
  ],
  declarations: [
  ],
  exports: [
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CustomInterceptor,
      multi: true
    },
    GoogleAuthService,
    ServerInfoService,
  ],
  bootstrap: [
    BasicWindowContainerComponent,
  ]
})
export class AppModule { }

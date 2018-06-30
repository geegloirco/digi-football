import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BasicWindowContainerComponent } from "./basic/component/basic-window-container/basic-window-container.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {GoogleAuthService} from "./service/google-auth/google-auth.service";
import {ServerInfoService} from "./service/server-info/server-info.service";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CustomInterceptor} from "./service/custom-browser-xhr/custom-browser-xhr.service";

import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {AppRoutingModule} from "./app-routing.module";
import { BasicModule } from "./basic/basic.module";
import {ViewModule} from "./view/view.module";
import {ControlModule} from "./control/control.module";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule.forRoot(),
    BasicModule,
    ControlModule,
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

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BasicModule } from "./basic/basic.module";
import {AppRoutingModule} from "./app-routing.module";
import { BasicWindowContainerComponent } from "./basic/component/basic-window-container/basic-window-container.component";
import { NavbarComponent } from "./component/navbar/navbar.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    BasicModule,
    AppRoutingModule,
  ],
  declarations: [
    NavbarComponent,
  ],
  providers: [],
  bootstrap: [ BasicWindowContainerComponent ]
})
export class AppModule { }

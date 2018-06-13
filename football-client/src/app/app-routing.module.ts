import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { BasicWindowContainerComponent } from "./basic/component/basic-window-container/basic-window-container.component";
import { NavbarComponent } from "./component/navbar/navbar.component";
import {DashboardComponent} from "./view/dashboard/dashboard.component";


const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent, children: [
  ] },

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { useHash: true })
  ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }

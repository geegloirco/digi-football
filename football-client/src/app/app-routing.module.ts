import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { BasicWindowContainerComponent } from "./basic/component/basic-window-container/basic-window-container.component";
import { NavbarComponent } from "./component/navbar/navbar.component";


const routes: Routes = [
  { path: '', redirectTo: '/navbar', pathMatch: 'full' },
  { path: 'navbar', component: NavbarComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { useHash: true })
  ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }

import {Component, Input, NgZone, OnInit, ViewChild} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Observable } from "rxjs/Observable";
import {
  GoogleAuthService,
  LoginModel
} from "../../service/google-auth/google-auth.service";
import {WindowSizeService} from "../../basic/service/window-size.service";
import {WindowSizeModel} from "../../basic/model/window-size.model";
import {NgbDropdown, NgbDropdownConfig} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: []

})
export class LoginComponent
  implements OnInit {
  userModel: LoginModel = new LoginModel('', false, true, false);

  @Input()
  isAdmin: boolean;

  menuState = false;
  windowSizeModel: WindowSizeModel;

  @ViewChild('myDrop') myDrop: NgbDropdown;

  constructor(
    private ngbConfig: NgbDropdownConfig,
    private windowSizeService: WindowSizeService,
    private currentActivatedRoute: ActivatedRoute,
    private zone: NgZone,
    public googleAuthService: GoogleAuthService,
    private router: Router) {
    this.ngbConfig.placement = 'bottom-right';
    this.ngbConfig.autoClose = false;
    // console.log('LoginViewComponent constructor');
    this.windowSizeModel = this.windowSizeService.getSize();
    this.windowSizeService.changeSize().subscribe(size => {
      this.windowSizeModel = size;
      this.menuState = false;
      this.myDrop.close();
    });
    // console.log("LoginViewComponent constructor")
    // console.log(this.currentActivatedRoute);
  }

  initAuth() {
    if(!this.googleAuthService.isAuthInitialized()) {
      this.googleAuthService.setGoogleClientId(
        '290205995528-qphql4q12vhali04vhrlao0ljqo80r3r.apps.googleusercontent.com');
      this.googleAuthService.checkServerLogin()
        .subscribe(res => {
          if (res != null) {
            this.zone.runTask(b => {
              this.userModel = res;
              console.log(res);
            });
          }
        });
    } else {
      this.userModel = this.googleAuthService.getUserModel();
    }
  }

  ngOnInit() {
    console.log('LoginViewComponent ngOnInit');

    this.initAuth();
    // if(!this.googleAuthService.isLoggedIn()) {
    //   console.log('checkServerLogin');
    //   this.googleAuthService.setGoogleClientId(
    //     '290205995528-qphql4q12vhali04vhrlao0ljqo80r3r.apps.googleusercontent.com');
    //   this.googleAuthService.checkServerLogin()
    //     .subscribe(res => {
    //       if (res != null) {
    //         this.zone.runTask(b => {
    //           this.userModel = res;
    //         });
    //       }
    //     });
    // } else {
    //   this.userModel = this.googleAuthService.getUserModel();
    // }
  }

  toggleClick($event) {
    $event.stopPropagation();
      this.menuState = !this.menuState;
      this.menuState ? this.myDrop.open() : this.myDrop.close();
  }

  closeLoginMenu() {
    this.menuState = false;
    this.myDrop.close();
  }

  doLogin() {
    let observer: Observable<LoginModel> = this.googleAuthService.login();
    if(observer != null) {
      // console.log("login observable is not null");
      observer.subscribe(res => {
        // console.log("doLogin subscribed!");
        this.zone.runTask(b => {
          this.userModel = res;
          // window.location.reload();
        });
      });
    } else {
      // console.log("login observable is null");
    }

  }

  doLogout() {
    this.googleAuthService.logout().subscribe(res => {
      this.zone.runTask(b => {
        this.userModel = res;
        // this.routeTrackingService.navigate('dashboard');
        this.closeLoginMenu();
        // this.router.navigate(['../dashboard'], {relativeTo: this.currentActivatedRoute});
      });
    });
  }
}

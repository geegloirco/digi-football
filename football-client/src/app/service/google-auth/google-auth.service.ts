import {Injectable, NgZone} from '@angular/core';
import { AppGlobals } from './app-globals';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {ServerInfoService} from "../server-info/server-info.service";
import {LoginModel} from "../../model/login.model";
// import {CartService} from "../../cart-service/cart.service";

declare const gapi: any;

@Injectable()
export class GoogleAuthService {
  googleClientId: string;
  userModel: LoginModel = null;
  googleInitialized = false;
  authInitialized = false;
  initUrl: string;
  loginUrl: string;
  logoutUrl: string;
  auth2: any;

  constructor(private http: HttpClient,
              // private cartService: CartService,
              private zone: NgZone,
              private serverInfoService: ServerInfoService) {
    console.log("GoogleAuthService constructor")
    this.initUrl = this.serverInfoService.getServerBaseUrl() + 'auth/check-existence';
    this.loginUrl = this.serverInfoService.getServerBaseUrl() + 'auth/login';
    this.logoutUrl = this.serverInfoService.getServerBaseUrl() + 'auth/logout';
  }

  isAuthInitialized(): boolean {
    return this.authInitialized;
  }

  isLoggedIn(): boolean {
    console.log("GoogleAuthService isLoggedIn");
    if(this.userModel == null || this.userModel == undefined) {
      return false;
    }
    return this.userModel.isLoggedIn;
  }

  getUserModel(): LoginModel {
    return this.userModel;
  }

  hasAdminRole(): boolean {
    if(this.userModel == null)
      return false;
    return this.userModel.isAdmin;
  }

  public checkServerLogin(): Observable<LoginModel> {
    const that = this;
    let ob = new Observable<LoginModel>(observer => {
      if(!this.googleInitialized) {
        // console.log("googleInitialized no initialized");
        this.initGoogle().subscribe(res => {
          that.http.get(this.initUrl)
            .subscribe(res => {
              that.authInitialized = true;
              if (res['status'] == 0) {
                that.userModel = res['entity'];
                this.userModel.isLoggedIn = true;
                // that.cartService.fetchFromServer();
                observer.next(that.userModel);
              }
              observer.next(null);
            });
        });
      } else {
        this.http.get(this.initUrl)
          .subscribe(res => {
            that.authInitialized = true;
            if (res['status'] == 0) {
              that.userModel = res['entity'];
              this.userModel.isLoggedIn = true;
              observer.next(that.userModel);
            }
            observer.next(null);
          });
      }
    });
    return ob;
  }

  public setGoogleClientId(googleClientId) {
    this.googleClientId = googleClientId;
  }

  public initGoogle(): Observable<boolean> {
    // console.log("init Google")
    var that = this;
    let ob = new Observable<boolean>(observer => {
      // let auth2: any;
      gapi.load('auth2', function () {
        gapi.auth2.init({
          client_id: that.googleClientId,
          cookiepolicy: 'single_host_origin',
          scope: 'profile email'
        }).then((res) => {
          // console.log("googleInitialized init true");
          that.auth2 = gapi.auth2.getAuthInstance();
          that.googleInitialized = true;
          observer.next(true);
        }, (err) => {
          // console.log("googleInitialized init false");
          observer.next(false);
        });
      });
    });
    return ob;
  }

  public login(): Observable<LoginModel> {
    const that = this;
    let ob = new Observable<LoginModel>(o => {
      if(this.googleInitialized) {
        this.loginGoogle().subscribe(res => {
          // that.cartService.fetchFromServer();
          o.next(res);
        });
      } else {
        this.initGoogle().subscribe(res => {
          that.loginGoogle().subscribe(res => {
            // that.cartService.fetchFromServer();
            o.next(res);
          });
        }, err => {
          o.next(null);
        });
      }
    });
    return ob;
  }

  private loginGoogle(): Observable<LoginModel> {
    const that = this;
    let ob = new Observable<LoginModel>(observer => {
      this.auth2.signIn().then((a) => {
        // console.log(a["Zi"].access_token);
        that.http.post(that.loginUrl,
          { code: a["Zi"].id_token }).subscribe(res => {
          // console.log("http get res")
          // console.log(res)
          that.userModel = res['entity'];
          that.userModel.isLoggedIn = true;
          observer.next(that.userModel);
        });
      });
    });
    return ob;
  }

  public logout(): Observable<LoginModel> {
    const that = this;
    let ob = new Observable<LoginModel>(o => {
      if(this.googleInitialized) {
        this.logoutGoogle().subscribe(res => {
          // this.cartService.resetCart();
          o.next(res);
        });
      } else {
        this.initGoogle().subscribe(res => {
          that.logoutGoogle().subscribe(res => {
            // this.cartService.resetCart();
            o.next(res);
          });
        }, err => {
          o.next(null);
        });
      }
    });
    return ob;
  }

  public logoutGoogle(): Observable<LoginModel> {
    const that = this;
    let ob = new Observable<LoginModel>(observer => {
      this.auth2.signOut().then(() => {
          that.http.get(that.logoutUrl)
            .subscribe(res => {
              if (res['status'] == 0) {
                that.userModel = new LoginModel(
                  res['entity']['username'], res['entity']['isAdmin'], res['entity']['isGuest'], false);
                observer.next(that.userModel);
                //Login button reference
              }
            });
        });
    });
    return ob;
  }
}

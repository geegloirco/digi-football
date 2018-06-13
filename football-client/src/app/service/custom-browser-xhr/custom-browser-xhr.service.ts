import {Injectable} from "@angular/core";
import {
  HttpEvent, HttpHandler, HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {Observable} from "rxjs/Rx";

@Injectable()
export class CustomInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    request = request.clone({
      withCredentials: true
    });

    return next.handle(request);
  }
}


// import {BrowserXhr} from "@angular/http";
// import {Injectable} from "@angular/core";
//
// @Injectable()
// export class CustomBrowserXhr extends BrowserXhr {
//   constructor() {}
//   build(): any {
//     let xhr = super.build();
//     xhr.withCredentials = true;
//     return <any>(xhr);
//   }
// }

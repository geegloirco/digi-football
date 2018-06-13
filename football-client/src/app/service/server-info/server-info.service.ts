import { Injectable } from '@angular/core';

@Injectable()
export class ServerInfoService {

  protocol: string = 'http';
  host: string = 'piana.ir'
  port: string = '9000';
  baseUrl: string = null;

  constructor() {
    if(this.protocol == '' && this.host == '' && this.port == '')
      this.baseUrl = 'http://localhost';
    else {
      this.baseUrl = this.protocol + '://';

      if(this.host)
        this.baseUrl += this.host;
      else
        this.baseUrl += "localhost";

      if(this.port) {
        if(this.port === '443' || this.port === '80') {

        } else {
          this.baseUrl += ':' + this.port;
        }
      }

      this.baseUrl += "/";
      console.log(this.baseUrl);
    }
  }

  getServerBaseUrl(): string {

    return this.baseUrl;
  }

}

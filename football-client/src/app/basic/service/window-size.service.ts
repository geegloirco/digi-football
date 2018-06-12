import {Injectable} from "@angular/core";
import { Observable } from "rxjs/Observable";
import { Subject } from "rxjs/Subject";

import { WindowRefService } from "./win-ref.service";
import { WindowSizeModel } from "../model/window-size.model";
import {BootstrapSizeEnum} from "../enum/bootstrap-size.enum";

@Injectable()
export class WindowSizeService {
  windowSize: WindowSizeModel;
  isSmall: boolean;
  bootstrapSize: BootstrapSizeEnum;
  width: number;
  height: number;
  subject = new Subject<WindowSizeModel>();

  constructor(private windowRef: WindowRefService) {
    this.setWindowSizeModel(
      windowRef.nativeWindow.innerWidth,
      windowRef.nativeWindow.innerHeight);
  }

  setWindowSizeModel(width, height) {
    this.width = width;
    this.height = height;
    if(width < 576) { //540
      this.isSmall = true;
      this.bootstrapSize = BootstrapSizeEnum.XS;
    } else if (width < 768) { //720
      this.isSmall = true;
      this.bootstrapSize = BootstrapSizeEnum.SM;
    } else if (width >= 1200) { //1140
      this.isSmall = false;
      this.bootstrapSize = BootstrapSizeEnum.XL;
    } else if (width >= 992) { //960
      this.isSmall = false;
      this.bootstrapSize = BootstrapSizeEnum.LG;
    } else if (width >= 768) { //960
      this.isSmall = false;
      this.bootstrapSize = BootstrapSizeEnum.MD;
    }

    this.windowSize = new WindowSizeModel(
      width, height, this.isSmall, this.bootstrapSize);
    this.subject.next(this.windowSize);
  }

  isModeSmall(): boolean {
    return this.isSmall;
  }

  changeSize(): Observable<WindowSizeModel> {
    return this.subject.asObservable();
  }
}

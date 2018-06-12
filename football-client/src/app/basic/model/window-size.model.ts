import {BootstrapSizeEnum} from "../enum/bootstrap-size.enum";

export class WindowSizeModel {
  width: number;
  height: number;
  isSmall: boolean;
  bootstrapSize: BootstrapSizeEnum;

  constructor(width: number,
              height: number,
              isSmall: boolean,
              bootstrapSize: BootstrapSizeEnum) {
    this.width = width;
    this.height = height;
    this.isSmall= isSmall;
    this.bootstrapSize = bootstrapSize;
  }
}

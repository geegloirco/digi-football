package ir.geeglo.business.rest.handler.asset;

import ir.piana.dev.webtool2.server.annotation.AssetHandler;
import ir.piana.dev.webtool2.server.annotation.Handler;
import ir.piana.dev.webtool2.server.annotation.HandlerType;

@Handler(baseUrl = "", handlerType = HandlerType.ASSET_HANDLER)
@AssetHandler(assetPath = "html-root")
public class RootAssetHandler {
}

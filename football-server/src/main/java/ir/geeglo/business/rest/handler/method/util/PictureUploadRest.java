package ir.geeglo.business.rest.handler.method.util;

import ir.geeglo.business.data.GeegloSpringServiceProvider;
import ir.geeglo.business.data.business.PictureBusiness;
import ir.geeglo.business.rest.model.PictureUploadModel;
import ir.piana.dev.webtool2.server.annotation.BodyObjectParam;
import ir.piana.dev.webtool2.server.annotation.Handler;
import ir.piana.dev.webtool2.server.annotation.MethodHandler;
import ir.piana.dev.webtool2.server.annotation.SessionParam;
import ir.piana.dev.webtool2.server.response.PianaResponse;
import ir.piana.dev.webtool2.server.role.RoleType;
import ir.piana.dev.webtool2.server.session.Session;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Handler(baseUrl = "image-uploader")
public class PictureUploadRest {
    @MethodHandler(requiredRole = RoleType.GUEST, httpMethod = "POST")
    public static PianaResponse reserveImage (
            @BodyObjectParam PictureUploadModel uploadModel,
            @SessionParam Session session) {
        PictureBusiness pictureBusiness = GeegloSpringServiceProvider
                    .getPictureBusiness();
        String key = pictureBusiness.add(uploadModel);
        if(key != null)
            return new PianaResponse(Response.Status.OK, 0, key);
        return new PianaResponse(Response.Status.OK, 1, "not exist");
    }

    @MethodHandler(requiredRole = RoleType.GUEST, httpMethod = "DELETE")
    public static PianaResponse removeImage (
            @QueryParam("image-key") String imageKey,
            @SessionParam Session session) {
        PictureBusiness pictureBusiness = GeegloSpringServiceProvider
                    .getPictureBusiness();
            if(pictureBusiness.remove(imageKey))
                return new PianaResponse(Response.Status.OK, 0, true);
        return new PianaResponse(Response.Status.OK, 1, false);
    }
}

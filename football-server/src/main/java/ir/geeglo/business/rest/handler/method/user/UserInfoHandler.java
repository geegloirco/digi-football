package ir.geeglo.business.rest.handler.method.user;

import ir.geeglo.business.data.GeegloSpringServiceProvider;
import ir.geeglo.business.rest.model.ExistanceModel;
import ir.geeglo.business.rest.model.UserModel;
import ir.piana.dev.webtool2.server.annotation.BodyObjectParam;
import ir.piana.dev.webtool2.server.annotation.Handler;
import ir.piana.dev.webtool2.server.annotation.MethodHandler;
import ir.piana.dev.webtool2.server.annotation.SessionParam;
import ir.piana.dev.webtool2.server.response.PianaResponse;
import ir.piana.dev.webtool2.server.role.RoleType;
import ir.piana.dev.webtool2.server.session.Session;

import javax.ws.rs.core.Response;

@Handler(baseUrl = "user/info")
public class UserInfoHandler {
    @MethodHandler(requiredRole = RoleType.GUEST)
    public static PianaResponse getUserInfo (
            @SessionParam Session session) {
        ExistanceModel existance = (ExistanceModel) session.getExistance();
        if(existance != null
                && existance.getUserModel() != null) {
            return new PianaResponse(Response.Status.OK, 0,
                    existance.getUserModel());
        }
        return new PianaResponse(Response.Status.OK, 1,
                "not exist");
    }

    @MethodHandler(requiredRole = RoleType.GUEST, httpMethod = "PUT")
    public static PianaResponse updateUserInfo (
            @BodyObjectParam UserModel userModel,
            @SessionParam Session session) {
        ExistanceModel existance = (ExistanceModel)session.getExistance();
        if(existance != null
                && existance.getUserModel() != null) {
            GeegloSpringServiceProvider.getUserBusiness()
                    .updateUserInfo(existance, userModel);
            return new PianaResponse(Response.Status.OK, 0,
                    existance.getUserModel());
        }
        return new PianaResponse(Response.Status.OK, 1,
                "not exist");
    }
}

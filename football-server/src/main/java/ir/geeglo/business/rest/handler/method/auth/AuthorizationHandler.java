package ir.geeglo.business.rest.handler.method.auth;

import ir.geeglo.business.data.GeegloSpringServiceProvider;
import ir.geeglo.business.data.business.UserBusiness;
import ir.geeglo.business.rest.model.ExistanceModel;
import ir.geeglo.business.rest.model.LoginModel;
import ir.piana.dev.webtool2.server.annotation.BodyObjectParam;
import ir.piana.dev.webtool2.server.annotation.Handler;
import ir.piana.dev.webtool2.server.annotation.MethodHandler;
import ir.piana.dev.webtool2.server.annotation.SessionParam;
import ir.piana.dev.webtool2.server.response.PianaResponse;
import ir.piana.dev.webtool2.server.role.RoleType;
import ir.piana.dev.webtool2.server.session.Session;
import org.apache.log4j.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Map;

@Handler(baseUrl = "auth")
public class AuthorizationHandler {
    static Logger logger = Logger.getLogger(AuthorizationHandler.class);

    public static ExistanceModel init (Session session) throws Exception {
        ExistanceModel existance = (ExistanceModel) session.getExistance();
        if(existance != null && existance.getUserModel() != null) {
            return existance;
        } else {
            try {
                existance = new ExistanceModel();
                session.setExistance(existance);
                return existance;
            } catch (Exception e) {
                throw e;
            }
        }
    }

    @MethodHandler(httpMethod = "GET", requiredRole = RoleType.GUEST)
    @Path("check-existence")
    public static PianaResponse checkExistance (
            @SessionParam Session session) {
        try {
            ExistanceModel existanceModel = init(session);
            LoginModel loginModel = new LoginModel(
                    existanceModel.getUsername(),
                    existanceModel.isAdmin(),
                    existanceModel.isGuest());
            return new PianaResponse(Response.Status.OK, 0, loginModel);
        } catch (Exception e) {
            e.printStackTrace();
            return new PianaResponse(
                    Response.Status.INTERNAL_SERVER_ERROR, null);
        }
    }

    @MethodHandler(httpMethod = "POST", requiredRole = RoleType.GUEST)
    @Path("login")
    public static PianaResponse login (
            @BodyObjectParam Map<String, String> code,
            @SessionParam Session session) throws Exception {

        ExistanceModel existanceModel = (ExistanceModel) session.getExistance();
        if (existanceModel == null) {
            existanceModel = init(session);
//            return new PianaResponse(Response.Status.UNAUTHORIZED, null);
        }
        UserBusiness businessService = null;
        try {
            businessService = GeegloSpringServiceProvider
                    .getUserBusiness();
        } catch (Exception e) {
            e.printStackTrace();
        }
        businessService.loginGoogle(existanceModel, code.get("code"));
        if (existanceModel != null)
            return new PianaResponse(Response.Status.OK, 0, new LoginModel(
                    ((ExistanceModel) existanceModel).getUsername(),
                    ((ExistanceModel) existanceModel).isAdmin(),
                    ((ExistanceModel) existanceModel).isGuest()));
        return new PianaResponse(Response.Status.INTERNAL_SERVER_ERROR, null);
    }

    @MethodHandler(httpMethod = "GET", requiredRole = RoleType.GUEST)
    @Path("logout")
    public static PianaResponse logout (
            @SessionParam Session session) throws Exception {
        ExistanceModel existanceModel = (ExistanceModel) session.getExistance();
        if (existanceModel == null) {
            return new PianaResponse(Response.Status.UNAUTHORIZED, null);
        }
        UserBusiness businessService = GeegloSpringServiceProvider
                .getUserBusiness();
        businessService.logout(existanceModel);
        return new PianaResponse(Response.Status.OK, 0, new LoginModel(
                ((ExistanceModel) existanceModel).getUsername(),
                ((ExistanceModel) existanceModel).isAdmin(),
                ((ExistanceModel) existanceModel).isGuest()));
    }
}

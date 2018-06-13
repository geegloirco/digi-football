package ir.geeglo.business.rest.handler.method.user;

import ir.geeglo.business.data.GeegloSpringServiceProvider;
import ir.geeglo.business.rest.model.ExistanceModel;
import ir.geeglo.business.rest.model.LocationModel;
import ir.piana.dev.webtool2.server.annotation.BodyObjectParam;
import ir.piana.dev.webtool2.server.annotation.Handler;
import ir.piana.dev.webtool2.server.annotation.MethodHandler;
import ir.piana.dev.webtool2.server.annotation.SessionParam;
import ir.piana.dev.webtool2.server.response.PianaResponse;
import ir.piana.dev.webtool2.server.role.RoleType;
import ir.piana.dev.webtool2.server.session.Session;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Handler(baseUrl = "user/location")
public class UserLocationHandler {
    @MethodHandler(requiredRole = RoleType.GUEST, sync = false)
    @Path("all")
    public static PianaResponse getLocations (
            @SessionParam Session session) {
        ExistanceModel existance = (ExistanceModel)session.getExistance();
        if(existance != null
                && existance.getLocationModels() != null) {
            return new PianaResponse(Response.Status.OK, 0,
                    existance.getLocationModels());
        }
        return new PianaResponse(Response.Status.OK, 1,
                "not exist");
    }

    @MethodHandler(requiredRole = RoleType.GUEST, httpMethod = "POST")
    public static PianaResponse saveLocations (
            @BodyObjectParam LocationModel locationModel,
            @SessionParam Session session) {
        ExistanceModel existance = (ExistanceModel)session.getExistance();
        if(existance != null
                && existance.getLocationModels() != null) {
            List<LocationModel> locationModels = GeegloSpringServiceProvider.getUserBusiness()
                    .addLocation(existance, locationModel);
            return new PianaResponse(Response.Status.OK, 0,
                    locationModels);
        }
        return new PianaResponse(Response.Status.OK, 1,
                "not exist");
    }
}

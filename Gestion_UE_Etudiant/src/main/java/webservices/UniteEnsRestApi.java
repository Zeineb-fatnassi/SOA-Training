package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/unites")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UniteEnsRestApi {

    private UniteEnseignementBusiness business = new UniteEnseignementBusiness();

    @POST
    public Response addUE(UniteEnseignement ue) {
        if (ue == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Corps de la requête manquant").build();
        }
        if (business.getUEByCode(ue.getCode()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Une UE avec le code " + ue.getCode() + " existe déjà").build();
        }
        business.addUniteEnseignement(ue);
        return Response.status(Response.Status.CREATED).entity(ue).build();
    }

    @GET
    public Response getUE(@QueryParam("semestre") Integer semestre) {
        List<UniteEnseignement> liste;
        if (semestre != null) {
            liste = business.getUEBySemestre(semestre);
        } else {
            liste = business.getListeUE();
        }
        return Response.ok(liste).build();
    }

    @GET
    @Path("/{code}")
    public Response getUEByCode(@PathParam("code") int code) {
        UniteEnseignement ue = business.getUEByCode(code);
        if (ue == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("UE introuvable : " + code).build();
        }
        return Response.ok(ue).build();
    }

    @DELETE
    @Path("/{code}")
    public Response deleteUE(@PathParam("code") int code) {
        if (business.deleteUniteEnseignement(code)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("UE introuvable : " + code).build();
    }

    @PUT
    @Path("/{code}")
    public Response updateUE(@PathParam("code") int code, UniteEnseignement ue) {
        if (ue == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Corps de la requête manquant").build();
        }
        ue.setCode(code);
        if (business.updateUniteEnseignement(code, ue)) {
            return Response.ok(ue).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("UE introuvable : " + code).build();
    }
}

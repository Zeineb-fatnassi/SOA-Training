package webservices;

import entities.Module;
import entities.UniteEnseignement;
import metiers.ModuleBusiness;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/modules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModuleRestApi {

    private ModuleBusiness moduleBusiness = new ModuleBusiness();
    private UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();

    @POST
    public Response addModule(Module module) {
        if (module == null || module.getMatricule() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Module ou matricule manquant").build();
        }
        if (module.getUniteEnseignement() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Le module doit être associé à une UE (uniteEnseignement.code)").build();
        }
        if (moduleBusiness.getModuleByMatricule(module.getMatricule()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Un module avec le matricule " + module.getMatricule() + " existe déjà").build();
        }
        if (moduleBusiness.addModule(module)) {
            return Response.status(Response.Status.CREATED).entity(module).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("UE introuvable : " + module.getUniteEnseignement().getCode()).build();
    }

    @GET
    public Response getAllModules() {
        return Response.ok(moduleBusiness.getAllModules()).build();
    }

    @GET
    @Path("/ue/{codeUE}")
    public Response getModulesByUE(@PathParam("codeUE") int codeUE) {
        UniteEnseignement ue = ueBusiness.getUEByCode(codeUE);
        if (ue == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("UE introuvable : " + codeUE).build();
        }
        return Response.ok(moduleBusiness.getModulesByUE(ue)).build();
    }

    @GET
    @Path("/{matricule}")
    public Response getModuleByMatricule(@PathParam("matricule") String matricule) {
        Module module = moduleBusiness.getModuleByMatricule(matricule);
        if (module == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Module introuvable : " + matricule).build();
        }
        return Response.ok(module).build();
    }

    @DELETE
    @Path("/{matricule}")
    public Response deleteModule(@PathParam("matricule") String matricule) {
        if (moduleBusiness.deleteModule(matricule)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Module introuvable : " + matricule).build();
    }

    @PUT
    @Path("/{matricule}")
    public Response updateModule(@PathParam("matricule") String matricule, Module module) {
        if (module == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Corps de la requête manquant").build();
        }
        Module existing = moduleBusiness.getModuleByMatricule(matricule);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Module introuvable : " + matricule).build();
        }
        module.setMatricule(matricule);

        if (module.getUniteEnseignement() != null) {
            UniteEnseignement ue = ueBusiness.getUEByCode(module.getUniteEnseignement().getCode());
            if (ue == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("UE introuvable : " + module.getUniteEnseignement().getCode()).build();
            }
            module.setUniteEnseignement(ue);
        } else {
            module.setUniteEnseignement(existing.getUniteEnseignement());
        }
        moduleBusiness.updateModule(matricule, module);
        return Response.ok(module).build();
    }
}

package graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import entities.Module;
import entities.UniteEnseignement;
import repository.ModuleBusiness;
import repository.UniteEnseignementBusiness;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final ModuleBusiness moduleBusiness;
    private final UniteEnseignementBusiness uniteEnseignementBusiness;

    public Query(ModuleBusiness moduleBusiness, UniteEnseignementBusiness uniteEnseignementBusiness) {
        this.moduleBusiness = moduleBusiness;
        this.uniteEnseignementBusiness = uniteEnseignementBusiness;
    }

    public List<Module> getallmodules() {
        return moduleBusiness.getAllModules();
    }

    public List<Module> getModulesByType(Module.TypeModule type) {
        return moduleBusiness.getModulesByType(type);
    }

    public Module getModuleByMatricule(String matricule) {
        return moduleBusiness.getModuleByMatricule(matricule);
    }

    public UniteEnseignement getUniteEnseignementByCode(int code) {
        return uniteEnseignementBusiness.getUEByCode(code);
    }

    public List<UniteEnseignement> getUniteEnseignementsByDomaine(String domaine) {
        return uniteEnseignementBusiness.getUEByDomaine(domaine);
    }

    public List<UniteEnseignement> getUniteEnseignementsBySemestre(int semestre) {
        return uniteEnseignementBusiness.getUEBySemestre(semestre);
    }
}

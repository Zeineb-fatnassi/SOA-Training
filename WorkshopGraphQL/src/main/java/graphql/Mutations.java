package graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import entities.Module;
import entities.UniteEnseignement;
import repository.ModuleBusiness;
import repository.UniteEnseignementBusiness;

public class Mutations implements GraphQLRootResolver {

    private final ModuleBusiness moduleBusiness;
    private final UniteEnseignementBusiness uniteEnseignementBusiness;

    public Mutations(ModuleBusiness moduleBusiness, UniteEnseignementBusiness uniteEnseignementBusiness) {
        this.moduleBusiness = moduleBusiness;
        this.uniteEnseignementBusiness = uniteEnseignementBusiness;
    }

    public boolean addModule(String matricule, String libelle, int coef, int volumeHoraire,
                             Module.TypeModule type, int codeUE) {
        UniteEnseignement ue = uniteEnseignementBusiness.getUEByCode(codeUE);
        if (ue == null || moduleBusiness.getModuleByMatricule(matricule) != null) {
            return false;
        }
        return moduleBusiness.addModule(new Module(matricule, libelle, coef, volumeHoraire, type, ue));
    }

    public Module updateModule(String matricule, String libelle, int coef, int volumeHoraire,
                               Module.TypeModule type, int codeUE) {
        UniteEnseignement ue = uniteEnseignementBusiness.getUEByCode(codeUE);
        if (ue == null) {
            return null;
        }
        Module updated = new Module(matricule, libelle, coef, volumeHoraire, type, ue);
        return moduleBusiness.updateModule(matricule, updated) ? updated : null;
    }

    public String deleteModule(String matricule) {
        return moduleBusiness.deleteModule(matricule) ? "deleted successfully" : "module not found";
    }

    public boolean addUniteEnseignement(int code, String domaine, String responsable, int credits, int semestre) {
        if (uniteEnseignementBusiness.getUEByCode(code) != null) {
            return false;
        }
        return uniteEnseignementBusiness.addUniteEnseignement(
                new UniteEnseignement(code, domaine, responsable, credits, semestre));
    }

    public UniteEnseignement updateUniteEnseignement(int code, String domaine, String responsable,
                                                     int credits, int semestre) {
        UniteEnseignement updated = new UniteEnseignement(code, domaine, responsable, credits, semestre);
        if (!uniteEnseignementBusiness.updateUniteEnseignement(code, updated)) {
            return null;
        }
        for (Module module : moduleBusiness.getAllModules()) {
            if (module.getUniteEnseignement() != null && module.getUniteEnseignement().getCode() == code) {
                module.setUniteEnseignement(updated);
            }
        }
        return updated;
    }

    public String deleteUniteEnseignement(int code) {
        return uniteEnseignementBusiness.deleteUniteEnseignement(code) ? "deleted successfully" : "unite enseignement not found";
    }
}

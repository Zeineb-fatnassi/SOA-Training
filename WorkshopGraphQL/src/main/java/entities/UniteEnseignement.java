package entities;

import java.util.Objects;


public class UniteEnseignement {
    private int code;
    private String domaine;
    private String responsable;
    private int credits;
    private int semestre;

    public UniteEnseignement() {
    }

    public UniteEnseignement(int code, String domaine, String responsable, int credits, int semestre) {
        this.code = code;
        this.domaine = domaine;
        this.responsable = responsable;
        this.credits = credits;
        this.semestre = semestre;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UniteEnseignement that)) return false;
        return getCode() == that.getCode() && getCredits() == that.getCredits() && getSemestre() == that.getSemestre() && Objects.equals(getDomaine(), that.getDomaine()) && Objects.equals(getResponsable(), that.getResponsable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getDomaine(), getResponsable(), getCredits(), getSemestre());
    }
}

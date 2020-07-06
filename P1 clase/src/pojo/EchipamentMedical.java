package pojo;

public enum EchipamentMedical {
    HALAT("Anexa 2"), MANUSI("Anexa 1"), MASCA("Anexa centrala");

    private  String depozitare;

    EchipamentMedical(String depozitare) {
        this.depozitare = depozitare;
    }

    public String getDepozitare() {
        return depozitare;
    }

    public void setDepozitare(String depozitare) {
        this.depozitare = depozitare;
    }
}

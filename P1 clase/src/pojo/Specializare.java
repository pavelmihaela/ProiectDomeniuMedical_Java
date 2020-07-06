package pojo;

public enum Specializare {

    CARDIOLOGIE(1),CHIRURGIE(2),NEUROLOGIE(3),ORTOPEDIE(4);
    private int codSpecializare;

    Specializare(int codSpecializare) {
        this.codSpecializare = codSpecializare;
    }

    public int getCodSpecializare() {
        return codSpecializare;
    }

    public void setCodSpecializare(int codSpecializare) {
        this.codSpecializare = codSpecializare;
    }
}


package models;

public enum Status {
    DISPONIVEL(1),
    INDISPONIVEL(2),
    EMPRESTADO(3);

    private final int id;
    Status(int value) {
        this.id = value;
    }

    public int getId() {
        return this.id;
    }

    public static Status parse(int value) {
        for (Status status : Status.values()) {
            if (status.getId() == value) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        switch (this.id){
            case 1:
                return "Disponível";
            case 2:
                return "Indisponível";
            case 3:
                return "Emprestado";
        }
        return "unknown";
    }
}

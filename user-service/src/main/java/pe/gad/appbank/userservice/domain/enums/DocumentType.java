package pe.gad.appbank.userservice.domain.enums;

public enum DocumentType {
    DNI("Documento Nacional de Identidad"),
    PASSPORT("Pasaporte"),
    DRIVER_LICENSE("Licencia de Conducir");

    private final String description;

    DocumentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

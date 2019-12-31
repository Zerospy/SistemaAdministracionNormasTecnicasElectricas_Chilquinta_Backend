package cl.desagen.chilquinta.enums;

public enum TipoNorma {

    NACIONAL(1),
    INTERNACIONAL(2),
    DOCUMENTO(3);

    public final Integer value;

    private TipoNorma(Integer value) {
        this.value = value;
    }
}

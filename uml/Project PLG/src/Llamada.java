import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("fdb13cf6-5d9f-4d42-9dac-7eb7ebd45ea4")
public class Llamada extends Instruccion {
    @objid ("1a085b39-a726-4243-85d1-093f8a70fe60")
    private String identificador;

    @objid ("62f651d2-7912-4d9b-9906-1b7701f68110")
    private String[] args = new String[n];

    @objid ("3c57e9a7-6f43-4a27-b96f-3f6e40ea349b")
    String getIdentificador() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.identificador;
    }

    @objid ("b2cfcb85-8025-4dfe-a959-0128b4d066ed")
    void setIdentificador(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.identificador = value;
    }

    @objid ("9b906f29-19e0-447c-bd04-8d65637af890")
    String[] getArgs() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.args;
    }

    @objid ("8dcc59fc-d14b-4b49-b7de-1a7ecdd6128f")
    void setArgs(String[] value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.args = value;
    }

}

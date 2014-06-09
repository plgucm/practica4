import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("0f72d763-6537-478e-a9ee-aff853298d99")
public class TipoID extends Tipo {
    @objid ("cddb8776-8d3e-458a-862f-c28029ddeb5a")
    private String identificador;

    @objid ("5b73a92f-83bf-4bdf-8d5f-c15b886b3e49")
    String getIdentificador() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.identificador;
    }

    @objid ("832f0bd2-28aa-418f-ac3e-606ed2310bf3")
    void setIdentificador(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.identificador = value;
    }

}

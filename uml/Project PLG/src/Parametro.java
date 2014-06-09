import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("19063dda-ff9e-4da5-b3e3-c3ac4ffab8e1")
public class Parametro {
    @objid ("0074ab15-3d0c-4391-9a71-318494a60bb3")
    private boolean porValor;

    @objid ("2edf0a50-53bc-438b-b465-77c204a1e6a5")
    private String identificador;

    @objid ("b9b43ddf-214b-4bfc-bdb6-b2d5a32e23e6")
    private List<Tipo> tipo = new ArrayList<Tipo> ();

    @objid ("7f112880-88ef-40cd-a948-0c8bf9c45f55")
    String getIdentificador() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.identificador;
    }

    @objid ("89d8b453-d79b-4a35-a4e3-602e190de8dc")
    void setIdentificador(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.identificador = value;
    }

    @objid ("49b227cb-72ca-40e4-997a-b64290581797")
    boolean isPorValor() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.porValor;
    }

    @objid ("48caa2ea-b382-4a3f-9039-b2c10e80120d")
    void setPorValor(boolean value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.porValor = value;
    }

}

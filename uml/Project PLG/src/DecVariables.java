import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("af459291-8b2b-4faf-964f-5bc760a35904")
public class DecVariables {
    @objid ("2aa97d1b-b288-492c-9b16-e9bc43441c0e")
    private String identificador;

    @objid ("ef68a785-49eb-45a5-8cfd-9d1dfce55327")
    private List<DecVariables> decVariables = new ArrayList<DecVariables> ();

    @objid ("22308408-dc94-4e2e-81dc-f3dd34a58fbf")
    private List<Tipo> tipo = new ArrayList<Tipo> ();

    @objid ("177c8aa4-5eff-4d59-87b5-31692c7980da")
    String getIdentificador() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.identificador;
    }

    @objid ("770accc4-ab1e-41f5-b172-6e6f70758e08")
    void setIdentificador(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.identificador = value;
    }

    @objid ("70f054d3-2f2a-4629-ab12-2aebea1f4bb9")
    DecVariables getDecVariables() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.decVariables;
    }

    @objid ("a30e255f-c766-47f7-b6c6-d6908108d7dc")
    void setDecVariables(DecVariables value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.decVariables = value;
    }

}

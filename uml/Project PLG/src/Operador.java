import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("78c9120f-d3ef-43c6-8b29-3eb8b9006e5b")
public abstract class Operador {
    @objid ("302fd87b-13dc-4e7b-a92d-8530976a2362")
    private String tipo;

    @objid ("02dc88f9-503e-49d8-bf24-45ce8f913ccf")
    String getTipo() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.tipo;
    }

    @objid ("780c648c-fbdd-4818-9fcc-a38d9480542f")
    void setTipo(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.tipo = value;
    }

}

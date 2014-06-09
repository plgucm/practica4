import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("a351fe3b-9ce7-4a62-a762-613f4b0cb8cf")
public abstract class Tipo {
    @objid ("6e5a86c7-f7ec-44d4-9eda-1dcb6c7144fa")
    protected Tipos tipo;

    @objid ("1bf94dc2-d874-463b-8ade-0603587cc243")
    Tipos getTipo() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.tipo;
    }

    @objid ("15e0e07f-c339-4a61-97d5-eb1ebba85d34")
    void setTipo(Tipos value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.tipo = value;
    }

}

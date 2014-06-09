import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("46788a0b-eb45-4968-aa5c-c629ccfc250b")
public class DecTipos {
    @objid ("4337f698-a4b5-4614-955c-2fa635faa8c8")
    private String identificador;

    @objid ("68e681de-1299-43b3-a258-ea040375a401")
    private List<DecTipos> decTipos = new ArrayList<DecTipos> ();

    @objid ("990398a5-402c-489a-93b8-7930c8e33f9b")
    private List<Tipo> tipo = new ArrayList<Tipo> ();

    @objid ("18407672-d8a2-4602-a70d-8fee290767f0")
    String getIdentificador() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.identificador;
    }

    @objid ("d9c49ac3-6404-4611-965e-11cf89189e85")
    void setIdentificador(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.identificador = value;
    }

    @objid ("3ae66ffc-0245-4abf-94d4-7958f3bac636")
    Tipo getTipo() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.tipo;
    }

    @objid ("71255620-110a-42fb-aba8-e0927bb44e0b")
    void setTipo(Tipo value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.tipo = value;
    }

    @objid ("3730c028-ef0c-4e4a-9515-02c8c4b6e218")
    DecTipos getDecTipos() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.decTipos;
    }

    @objid ("6eabdd2c-3913-48a6-a73e-69a44b4c5494")
    void setDecTipos(DecTipos value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.decTipos = value;
    }

}

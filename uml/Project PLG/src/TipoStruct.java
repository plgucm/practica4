import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("310282ed-4e63-4ece-a00a-27efbc4da8ed")
public class TipoStruct extends Tipo {
    @objid ("24975f5d-6f75-4c79-b450-018778435926")
    private String[] identificadores = new String[n];

    @objid ("09d35757-e249-4a22-937e-a1931ca556b6")
    private List<Tipo> tipo = new ArrayList<Tipo> ();

    @objid ("007108ae-6bb9-4e60-af66-ddb6f38624c0")
    String[] getIdentificadores() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.identificadores;
    }

    @objid ("3a5caf1d-98c0-443a-9904-a5006d7cf38c")
    void setIdentificadores(String[] value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.identificadores = value;
    }

}

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("bcb779b8-d24a-4369-b953-3187bfaa181e")
public class TipoPuntero extends Tipo {
    @objid ("5c2b9406-1519-4058-a4d7-f0f78384fb70")
    private List<Tipo> tipo = new ArrayList<Tipo> ();

    @objid ("23777363-a51d-4ed8-8170-553a5b634041")
    List<Tipo> getTipo() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.tipo;
    }

    @objid ("4ede400c-cc83-4d29-8b8f-165860948918")
    void setTipo(List<Tipo> value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.tipo = value;
    }

}

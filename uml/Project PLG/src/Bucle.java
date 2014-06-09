import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("7c88e2a3-660b-4855-b3f4-2dda07ab246e")
public class Bucle extends Instruccion {
    @objid ("6b825765-067e-4090-a696-1de42c7be0b6")
    private List<Casos> casos = new ArrayList<Casos> ();

    @objid ("226bd856-7016-4c37-810d-07769dd2fb11")
    List<Casos> getCasos() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.casos;
    }

    @objid ("b3ca2e66-eda9-4096-90b3-0b4c843992d0")
    void setCasos(List<Casos> value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.casos = value;
    }

}

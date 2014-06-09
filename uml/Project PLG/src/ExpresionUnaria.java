import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("09fb5a71-240a-4c27-aa4e-468c34541bd0")
public class ExpresionUnaria extends Expresion {
    @objid ("ea486143-d49c-4317-9767-2f8e40dc301c")
    private Expresion exp;

    @objid ("84ecab0b-e799-4f9e-a182-e19f43b663db")
    private List<OpUnario> opUnario = new ArrayList<OpUnario> ();

    @objid ("1039b7da-79da-4d75-a208-5a15c47bb2b9")
    Expresion getExp() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.exp;
    }

    @objid ("a4d95b13-ab55-42c9-9019-5533f0250d1a")
    void setExp(Expresion value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.exp = value;
    }

}

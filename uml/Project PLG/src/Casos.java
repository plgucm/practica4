import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("a9fef73d-cff4-4b56-bf07-fc3384050fed")
public class Casos {
    @objid ("4c512b79-eaaf-4903-8e90-348eda0791fd")
    private List<Bloque> bloque = new ArrayList<Bloque> ();

    @objid ("41806000-aa96-4806-8325-49d95087dc9d")
    private List<Casos> casos = new ArrayList<Casos> ();

    @objid ("d729822b-a008-4613-9aac-742bd0bff794")
    private List<Expresion> expresion = new ArrayList<Expresion> ();

}

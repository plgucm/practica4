import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("2865d571-4a84-4083-ba22-75248efb32a5")
public class Programa {
    @objid ("505304f9-104e-497e-bed9-97e3d25f29f3")
    private List<DecTipos> decTipos = new ArrayList<DecTipos> ();

    @objid ("bc1d08e0-6400-4d43-904a-7441c3b54b30")
    private List<DecVariables> decVariables = new ArrayList<DecVariables> ();

    @objid ("69a736d5-433e-4ab9-a44f-385abd2a99c3")
    private List<DecSubprogramas> decSubprogramas = new ArrayList<DecSubprogramas> ();

    @objid ("7be61c14-392f-4fec-9196-e0d293b03dc1")
    private List<Bloque> bloque = new ArrayList<Bloque> ();

}

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("eba879f1-9d57-4df5-8a75-0f097cf8917f")
public class Asignacion extends Instruccion {
    @objid ("29cc55ef-bed2-49ab-b71a-9f8f231e43a8")
    private List<Designador> designador = new ArrayList<Designador> ();

    @objid ("7b08de39-e67f-41e2-9a5f-224341ad5f96")
    private List<Expresion> expresion = new ArrayList<Expresion> ();

}

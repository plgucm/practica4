import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("db80d45f-6799-4987-92ea-5289420f5b8d")
public class Designador {
    @objid ("3a79dd4f-f79d-4c16-88c4-3472227e4c8f")
    private String identificador;

    @objid ("4d7ece49-907f-4d9c-9eb6-f87c17505b34")
    private List<Expresion> expresion = new ArrayList<Expresion> ();

    @objid ("56a0671a-ef1b-438c-8e7f-5220e1d8d2b0")
    private List<Designador> designador = new ArrayList<Designador> ();

    @objid ("1f60c95a-7df1-4529-bdec-a14885e45afc")
    String getIdentificador() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.identificador;
    }

    @objid ("2d6149e2-ffe0-4eee-9eff-c429cc3e0211")
    void setIdentificador(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.identificador = value;
    }

    @objid ("7afdf53d-023d-474a-929c-6dd92553d758")
    List<Expresion> getExpresion() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.expresion;
    }

    @objid ("734d4e89-09c8-4134-a0e4-576543ab6f9d")
    void setExpresion(List<Expresion> value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.expresion = value;
    }

}

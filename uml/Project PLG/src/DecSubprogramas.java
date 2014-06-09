import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("83e482bb-79a8-4f9a-bb28-421084ea69f6")
public class DecSubprogramas {
    @objid ("619b2170-8577-4cea-9ec4-72fa0a0dc52a")
    private String identificador;

    @objid ("98d9a466-b2c2-4b68-80d6-e2a1a69c8d55")
    private List<DecSubprogramas> decSubprogramas = new ArrayList<DecSubprogramas> ();

    @objid ("591ad9aa-f82d-4de0-9903-6d5a0448571d")
    private List<Parametro> parametros = new ArrayList<Parametro> ();

    @objid ("4820498f-8bb5-4f50-802a-cc8cdd89def0")
    private List<Programa> programa = new ArrayList<Programa> ();

    @objid ("4a9a8429-a838-4ed9-9670-98a93a805690")
    String getIdentificador() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.identificador;
    }

    @objid ("127bb958-cce4-43ea-bd17-10a13f047777")
    void setIdentificador(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.identificador = value;
    }

    @objid ("085c45c7-5131-40fa-a16d-97364423bdc2")
    DecSubprogramas getDecSubprogramas() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.decSubprogramas;
    }

    @objid ("d6fe98b3-0385-4aab-9f3d-2a2e8d1a327c")
    void setDecSubprogramas(DecSubprogramas value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.decSubprogramas = value;
    }

    @objid ("81c5c550-156b-4ae7-950d-0d5352b1711e")
    List<Programa> getPrograma() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.programa;
    }

    @objid ("37537cd8-c587-4c74-8feb-54cf6a44cbc9")
    void setPrograma(List<Programa> value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.programa = value;
    }

}

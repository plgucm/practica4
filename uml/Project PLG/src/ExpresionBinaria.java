import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("66c31182-92d4-4d54-9d06-6755b77bb268")
public class ExpresionBinaria extends Expresion {
    @objid ("4c380820-c4d6-4304-8843-751f99eb091c")
    private Expresion exp0;

    @objid ("6d029946-0b83-4361-b08b-fe844f8412af")
    private Expresion exp1;

    @objid ("90a9f174-9f5f-4a2c-8834-c0538838faaa")
    private OpBinario opBinario;

    @objid ("a28b555c-c7ac-4038-8a74-3fcd3b9ad0e9")
    Expresion getExp0() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.exp0;
    }

    @objid ("c1334a5d-cb26-4f46-abe0-986ac369dc7e")
    void setExp0(Expresion value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.exp0 = value;
    }

    @objid ("6005724d-02fc-4b6a-a266-0ac255e58993")
    Expresion getExp1() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.exp1;
    }

    @objid ("e558310c-520d-4686-aba8-7d5bfc9965fa")
    void setExp1(Expresion value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.exp1 = value;
    }

}

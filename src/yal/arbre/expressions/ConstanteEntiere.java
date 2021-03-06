package yal.arbre.expressions;

public class ConstanteEntiere extends Constante {
    
    public ConstanteEntiere(String texte, int n) {
        super(texte, n) ;
    }

    @Override
    public String toMIPS() {
        StringBuffer mips = new StringBuffer();
        mips.append("\t#Constante entiere\n");
        mips.append("\tli $v0, " + cste + "\n");
        return mips.toString();
    }

    @Override
    public String type() {
        return "int";
    }

}

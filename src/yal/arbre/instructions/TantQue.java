package yal.arbre.instructions;

import yal.arbre.ArbreAbstrait;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;
import yal.factories.NumFactory;

public class TantQue extends Instruction {

    protected Expression condition;
    protected ArbreAbstrait instructions ;

    public TantQue(Expression exp, ArbreAbstrait intrs, int n){
        super(n);
        condition = exp;
        instructions = intrs;
    }

    @Override
    public void verifier() {
        condition.verifier();

        if (!condition.type().equals("bool")){
            throw new AnalyseSemantiqueException(noLigne, "La condition n'est pas un booléen");
        }

        instructions.verifier();
    }

    public boolean verifierRetourne(){
        boolean yaretourne = instructions.verifierRetourne();
        if (!yaretourne){
            throw new AnalyseSemantiqueException(noLigne, "Il n'y a pas d'instruction RETOURNE accesible dans toutes les branches du SI");
        }

        return yaretourne;
    }

    @Override
    public String toMIPS() {
        int numTantque = NumFactory.getInstance().getNumTantque();
        StringBuffer mips = new StringBuffer();
        mips.append("\tTantQue" + numTantque + " : #Tantque" + numTantque + "\n");

        mips.append(condition.toMIPS());

        mips.append("\tbeq $v0, 0");

        mips.append(" FinTantQue" + numTantque + " #Aller a fintantque" + numTantque + "\n\n");

        mips.append(instructions.toMIPS());

        mips.append("\tb TantQue" + numTantque + " #Aller a tantque" + numTantque + "\n");

        mips.append("\tFinTantQue" + numTantque + " : #Fintantque" + numTantque + "\n");
        return mips.toString();
    }
}

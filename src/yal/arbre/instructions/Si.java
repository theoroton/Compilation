package yal.arbre.instructions;

import yal.arbre.ArbreAbstrait;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;
import yal.factories.NumFactory;

public class Si extends Instruction {

    protected Expression condition;
    protected ArbreAbstrait instructions_Si;
    protected ArbreAbstrait instructions_Sinon;

    public Si(Expression exp, ArbreAbstrait si, ArbreAbstrait sinon, int n){
        super(n);
        condition = exp;
        instructions_Si = si;
        instructions_Sinon = sinon;
    }

    @Override
    public void verifier() {
        condition.verifier();

        if (!condition.type().equals("bool")){
            throw new AnalyseSemantiqueException(noLigne, "La condition n'est pas un booléen");
        }

        if (instructions_Si != null){
            instructions_Si.verifier();
        }


        if (instructions_Sinon != null) {
            instructions_Sinon.verifier();
        }
    }

    public boolean verifierRetourne(){
        boolean yaRSi;
        if (instructions_Si != null){
            yaRSi = instructions_Si.verifierRetourne();
        } else {
            yaRSi = true;
        }

        boolean yaRSinon;
        if (instructions_Sinon != null) {
            yaRSinon = instructions_Sinon.verifierRetourne();
        } else {
            yaRSinon = true;
        }

        boolean yaretourne = yaRSi && yaRSinon;
        if (!yaretourne){
            throw new AnalyseSemantiqueException(noLigne, "Il n'y a pas d'instruction RETOURNE accesible dans toutes les branches du SI");
        }

        return yaretourne;
    }

    @Override
    public String toMIPS() {
        int numSi = NumFactory.getInstance().getNumSi();
        StringBuffer mips = new StringBuffer();
        mips.append("\tSi" + numSi + " : #Si" + numSi + "\n");

        mips.append(condition.toMIPS());

        mips.append("\tbeq $v0, 0");

        if (instructions_Sinon != null) {
            mips.append(" Sinon" + numSi + " #Aller a sinon" + numSi + "\n\n");
        } else {
            mips.append(" FinSi" + numSi + " #Aller a finsi" + numSi + "\n\n");
        }

        if (instructions_Si != null){
            mips.append(instructions_Si.toMIPS());
        }

        mips.append("\tb FinSi" + numSi + " #Aller a finsi" + numSi + "\n\n");

        if (instructions_Sinon != null) {
            mips.append("\tSinon" + numSi + " : #Sinon" + numSi + "\n");
            mips.append(instructions_Sinon.toMIPS());
        }

        mips.append("\tFinSi" + numSi + " : #Finsi" + numSi + "\n");
        return mips.toString();
    }
}

import java.util.Arrays;
import java.util.Vector;

public class SelectFromCommand extends XCommand{
    private String nomRelation;
    private Vector<String> colonne;
    private Vector<String> criteres;

    public SelectFromCommand(String command) {
        colonne = new Vector<String>();
        criteres = new Vector<String>();

        String[] tokens = command.split(" ");
        nomRelation = tokens[3];
        String[] c = tokens[1].split(",");

        if(tokens[1].equals("*")){
            RelationInfo ri = Catalog.getSingleton().getRelationInfo(nomRelation);
            for(ColInfo ci: ri.getTabInfo()){
                colonne.add(ci.getColonne());
            }

        }else{
            for (int i = 0; i < c.length; i++) {
                colonne.add(c[i]);
            }
        }

        if(tokens.length > 4){
            tokens = Arrays.copyOfRange(tokens, 5, tokens.length);
            for (int i = 0; i < tokens.length; i+=2) {
                criteres.add(tokens[i]);
            }
        }
    }

    public void execute() {
        //TODO
    }
}
    
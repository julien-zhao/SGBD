import java.util.Arrays;
import java.util.Vector;

public class SelectFromCommand extends XCommand{
    private RelationInfo relation;
    private Vector<String> colonne;
    private Vector<String> criteres;

    public SelectFromCommand(String command) {
        colonne = new Vector<String>();
        criteres = new Vector<String>();

        String[] tokens = command.split(" ");
        relation = Catalog.getSingleton().getRelationInfo(tokens[3]);
        String[] c = tokens[1].split(",");

        if(tokens[1].equals("*")){
            for(ColInfo ci: relation.getTabInfo()){
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
        
    }
}
    
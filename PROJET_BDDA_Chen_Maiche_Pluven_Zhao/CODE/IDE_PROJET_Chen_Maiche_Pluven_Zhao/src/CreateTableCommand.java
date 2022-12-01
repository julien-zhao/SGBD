import java.io.IOException;
import java.util.Vector;

public class CreateTableCommand extends XCommand{
    /**
     * le nom de la relation (chaine de caractères)
     */
	private String nomRelation;
	/**
	 * le nombre de colonnes (entiers)
	 */
    private int nbColonnes;
    /**
     * les noms des colonnes (liste ou tableau de chaînes de caractères)
     */
    private Vector<String> nomColonnes;
    /**
     * les types des colonnes (liste ou tableau de chaînes de caractères 
     */
    private Vector<String> typeColonnes;

    public CreateTableCommand(String command) {
        nomColonnes = new Vector<String>();
        typeColonnes = new Vector<String>();
        
        String[] tokens = command.split(" ");
        nomRelation = tokens[2];
        String[] colonnes = tokens[3].split(",");
        colonnes[0] = colonnes[0].substring(1, colonnes[0].length());
        colonnes[colonnes.length - 1] = colonnes[colonnes.length - 1].substring(0, colonnes[colonnes.length - 1].length() - 1);

        nbColonnes = colonnes.length;

        for (int i = 0; i < nbColonnes; i++) {
            String[] colonne = colonnes[i].split(":");
            nomColonnes.add(colonne[0]);
            typeColonnes.add(colonne[1]);
        }
    }

    public void execute() throws IOException{
        FileManager fm = FileManager.getSingleton();
        PageId pId = fm.createNewHeaderPage();
        RelationInfo ri = new RelationInfo(nomRelation, pId);

        for (int i = 0; i < nbColonnes; i++) {
            ri.addColonne(nomColonnes.get(i), typeColonnes.get(i));
        }

        Catalog.getSingleton().addRelationInfo(ri);
    }
}
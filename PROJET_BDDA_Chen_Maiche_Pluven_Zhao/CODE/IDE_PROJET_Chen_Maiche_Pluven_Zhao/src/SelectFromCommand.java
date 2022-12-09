import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class SelectFromCommand extends XCommand{
    private RelationInfo relation;
    private Vector<String> colonne;
    private Vector<Critere> criteres;

    public SelectFromCommand(String command) {
        colonne = new Vector<String>();
        criteres = new Vector<Critere>();

        String[] tokens = command.split(" ");
        relation = Catalog.getSingleton().getRelationInfo(tokens[3]);
        if(relation == null){
            System.out.println("Relation " + tokens[3] + " does not exist");
            return;
        }

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
                criteres.add(new Critere(tokens[i]));
            }
        }
    }

    public void execute() throws IOException {
        if (relation == null) {
            System.out.println("Relation " + relation + " does not exist");
            return;
        }
        for(int i = 0; i < colonne.size(); i++) {
            if(!relation.getTabInfo().contains(new ColInfo(colonne.get(i), ""))){
                System.out.println("Column " + colonne.get(i) + " does not exist");
                return;
            }
        }
        for(int i = 0; i < criteres.size(); i++) {
            if(!relation.getTabInfo().contains(new ColInfo(criteres.get(i).col, ""))){
                System.out.println("Column " + criteres.get(i).col + " does not exist");
                return;
            }
        }
        FileManager fm = FileManager.getSingleton();
        Vector<Record> records = fm.getRecordsInRelation(relation);

        for(Record r : records){
            System.out.println(r.getValues());
            boolean ok = true;
            for(Critere c : criteres){

                if(!c.execute(r)){
                    ok = false;
                    break;
                }
            }
            if(ok){
                for(String s : colonne){
                    System.out.print(r.getColValue(s) + " ");
                }
                System.out.println();
            }
        }
    }

    class Critere {
        private String col;
        private String op;
        private String val;

        public Critere(String txt) {
            String[] tokens;
            if(txt.contains("=")){
                op = "=";
                tokens = txt.split("=");
                col = tokens[0];
                val = tokens[1];
            }
            if(txt.contains(">")){
                op = ">";
                tokens = txt.split(">");
                col = tokens[0];
                val = tokens[1];
            }
            if(txt.contains("<")){
                op = "<";
                tokens = txt.split("<");
                col = tokens[0];
                val = tokens[1];
            }
            if(txt.contains(">=")){
                op = ">=";
                tokens = txt.split(">=");
                col = tokens[0];
                val = tokens[1];
            }
            if(txt.contains("<=")){
                op = "<=";
                tokens = txt.split("<=");
                col = tokens[0];
                val = tokens[1];
            }
            if(txt.contains("<>")){
                op = "<>";
                tokens = txt.split("<>");
                col = tokens[0];
                val = tokens[1];
            }

            if(!colonne.contains(col)){
                System.out.println("Column " + col + " does not exist");
                return;
            }

            if(!val.matches("[0-9]+.[0-9]+") && !val.matches("[0-9]+")){
                if(!op.equals("=")||!op.equals("<>")){
                    System.out.println("Operator " + op + " is not valid for string");
                    return;
                }
                
            }
        }

        public boolean execute(Record r){
            
            String eCol = r.getValues().get(relation.getColonneIndex(col));

            if(op.equals("=")){
                return eCol.equals(val);
            }
            if(op.equals(">")){
                return eCol.compareTo(val) > 0;
            }
            if(op.equals("<")){
                return eCol.compareTo(val) < 0;
            }
            if(op.equals(">=")){
                return eCol.compareTo(val) >= 0;
            }
            if(op.equals("<=")){
                return eCol.compareTo(val) <= 0;
            }
            if(op.equals("<>")){
                return !eCol.equals(val);
            }
            return false;
        }
    }
}

    
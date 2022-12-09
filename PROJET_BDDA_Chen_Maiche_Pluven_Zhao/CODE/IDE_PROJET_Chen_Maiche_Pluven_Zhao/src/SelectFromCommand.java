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
<<<<<<< HEAD
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
=======
                criteres.add(tokens[i]);
>>>>>>> 632482ff038ec9807d1b01225ff7eb14045a1d5d
            }
        }
    }

<<<<<<< HEAD
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

            if(val.contains(")")){
                val = val.substring(0, val.length()-1);
            }

            if(!relation.getTabInfo().contains(new ColInfo(col, ""))){
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

            relation.getColonneType(col);
            if(relation.getColonneType(col).startsWith("VARCHAR")){
                return executeVARCHAR(eCol);
            }
            if(relation.getColonneType(col).equals("INTEGER")){
                int eColInt = Integer.parseInt(eCol);
                return executeINTEGER(eColInt);
            }
            if(relation.getColonneType(col).equals("REAL")){
                double eColDouble = Double.parseDouble(eCol);
                return executeREAL(eColDouble);
            }
            return false;
        }

        private boolean executeINTEGER(int eCol) {
            double valInt = Double.parseDouble(val);
            if(op.equals("=")){
                return eCol == valInt;
            }
            if(op.equals("<>")){
                return eCol != valInt;
            }
            if(op.equals(">")){
                return eCol > valInt;
            }
            if(op.equals("<")){
                return eCol < valInt;
            }
            if(op.equals(">=")){
                return eCol >= valInt;
            }
            if(op.equals("<=")){
                return eCol <= valInt;
            }
            return false;
        }

        private boolean executeREAL(double eCol) {
            double valInt = Double.parseDouble(val);
            if(op.equals("=")){
                return eCol == valInt;
            }
            if(op.equals("<>")){
                return eCol != valInt;
            }
            if(op.equals(">")){
                return eCol > valInt;
            }
            if(op.equals("<")){
                return eCol < valInt;
            }
            if(op.equals(">=")){
                return eCol >= valInt;
            }
            if(op.equals("<=")){
                return eCol <= valInt;
            }
            return false;
        }

        private boolean executeVARCHAR(String eCol) {
            if(op.equals("=")){
                return eCol.equals(val);
            }
            if(op.equals("<>")){
                return !eCol.equals(val);
            }
            return false;
        }

        public String toString() {
            return col + " " + op + " " + val;
        }
=======
    public void execute() {
        //TODO
>>>>>>> 632482ff038ec9807d1b01225ff7eb14045a1d5d
    }
}
    
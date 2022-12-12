import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class SelectFromCommand extends XCommand{
    private RelationInfo relation;
    private RelationInfo relationJoin; 
    private Vector<String> colonne;
    private Vector<Critere> criteres;
    private boolean isJoin = false;

    public SelectFromCommand(String command) {
        colonne = new Vector<String>();
        criteres = new Vector<Critere>();

        String[] tokens = command.split(" ");


        if(tokens[3].contains(",")){
            isJoin = true;
            String[] r = tokens[3].split(",");
            relation = Catalog.getSingleton().getRelationInfo(r[0]);
            relationJoin = Catalog.getSingleton().getRelationInfo(r[1]);
            if(relation == null){
                System.out.println("Relation " + r[0] + " does not exist");
                return;
            }
            if(relationJoin == null){
                System.out.println("Relation " + r[1] + " does not exist");
                return;
            }
        }else{
            relation = Catalog.getSingleton().getRelationInfo(tokens[3]);
            if(relation == null){
                System.out.println("Relation " + tokens[3] + " does not exist");
                return;
            }
        }   

        String[] c = tokens[1].split(",");

        if(tokens[1].equals("*")){
            if(isJoin){
                for(ColInfo ci: relation.getTabInfo()){
                    colonne.add(relation.getNomRelation()+"."+ci.getColonne());
                }
                for(ColInfo ci: relationJoin.getTabInfo()){
                    colonne.add(relationJoin.getNomRelation()+"."+ci.getColonne());
                }
            }
            else{
                for(ColInfo ci: relation.getTabInfo()){
                    colonne.add(ci.getColonne());
                }
            }

        }else{
            for (int i = 0; i < c.length; i++) {
                colonne.add(c[i]);
            }
        }

        if(tokens.length > 4){
            tokens = Arrays.copyOfRange(tokens, 5, tokens.length);
            if(isJoin){
                for (int i = 0; i < tokens.length; i+=4) {
                    criteres.add(new Critere(tokens[i]));
                }
            }else{
                for (int i = 0; i < tokens.length; i+=2) {
                    criteres.add(new Critere(tokens[i]));
                }
            }
        }
    }

    public void execute() throws IOException {

        if(isJoin){
            executeJoin();
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

        int nb = 0;
        for(Record r : records){
            boolean ok = true;
            for(Critere c : criteres){
                if(!c.execute(r)){
                    ok = false;
                    break;
                }
            }
            if(ok){
                String c = "";
                for(String s : colonne){
                    c+= r.getColValue(s) + " ; "; //5 ; 5 .
                }
                //delete last caracter of c
                c = c.substring(0, c.length()-2);
                c+= ".";
                System.out.println(c);
                nb++;
            }
           
        }
        System.out.println("Total records = " + nb);
    }

    private void executeJoin() throws IOException {
        FileManager fm = FileManager.getSingleton();
        Vector<PageId> pagesR1 = fm.getAllDataPages(relation);
        int nb = 0;
        for(PageId p: pagesR1){
            RecordIterator ri = new RecordIterator(relation,p);
            Vector<PageId> pagesR2 = fm.getAllDataPages(relationJoin);
            for(PageId p2 : pagesR2){
                RecordIterator ri2 = new RecordIterator(relationJoin,p2);
                while(ri.hasNext()){
                    Record r1 = ri.getNextRecord();
                    while(ri2.hasNext()){
                        Record r2 = ri2.getNextRecord();
                        boolean ok = true;
                        for(Critere c : criteres){
                            if(!c.execute(r1,r2)){
                                ok = false;
                                break;
                            }
                        }
                        if(ok){
                            String c = "";
                            for(String s : colonne){
                                if(s.contains(".")){
                                    String[] t = s.split("\\.");
                                    if(t[0].equals(relation.getNomRelation())){
                                        c+= r1.getColValue(t[1]) + " ; "; 
                                    }else{
                                        c+= r2.getColValue(t[1]) + " ; "; 
                                    }
                                }else{
                                    c+= r1.getColValue(s) + " ; "; 
                                }
                            }
                            //delete last caracter of c
                            c = c.substring(0, c.length()-2);
                            c+= ".";
                            System.out.println(c);
                            nb++;
                        }
                    }
                }
                ri2.close();
                ri.reset();
            }
            ri.close();
        }
        System.out.println("Total records = " + nb);
        
    }

    class Critere {
        private String col;
        private String op;
        private String val;
        private String r1;
        private String r2;

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

            if(isJoin){ 
                String[] rel1 = col.split("\\.");
                r1 = rel1[0];
                String c = rel1[1];

                String[] rel2 = val.split("\\.");
                r2 = rel2[0];
                String v = rel2[1];
                
                if(r1.equals(relation.getNomRelation())){
                    if(!relation.getTabInfo().contains(new ColInfo(c, ""))){
                        System.out.println("Column " + c + " does not exist");
                        return;
                    }
                }
                if(r1.equals(relationJoin.getNomRelation())){
                    if(!relationJoin.getTabInfo().contains(new ColInfo(c, ""))){
                        System.out.println("Column " + c + " does not exist");
                        return;
                    }
                }

                if(r2.equals(relation.getNomRelation())){
                    if(!relation.getTabInfo().contains(new ColInfo(v, ""))){
                        System.out.println("Column " + v + " does not exist");
                        return;
                    }
                }
                if(r2.equals(relationJoin.getNomRelation())){
                    if(!relationJoin.getTabInfo().contains(new ColInfo(v, ""))){
                        System.out.println("Column " + v + " does not exist");
                        return;
                    }
                }

            }else{
                if(!relation.getTabInfo().contains(new ColInfo(col, ""))){
                    System.out.println("Column " + col + " does not exist");
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
            int res = eCol.compareToIgnoreCase(val);
            if(op.equals(">")&&(res>0)){
            	return true;
            }
            if(op.equals("<")&&(res<0)){
            	return true;
            }
            if(op.equals(">=")&&(res>=0)){
                return true;
            }
            if(op.equals("<=")&&(res<=0)){
                return true;
            }
            return false;
        }

        //execute for join
        public boolean execute(Record r, Record rJoin){
            String eCol ="";
            String vCol = col.split("\\.")[1];
            if(r1.equals(relation.getNomRelation())){
                eCol = r.getValues().get(relation.getColonneIndex(vCol));
            }else{
                eCol = r.getValues().get(relationJoin.getColonneIndex(vCol));
            }

            String eColJoin ="";
            String vVal = val.split("\\.")[1];
            if(r2.equals(relation.getNomRelation())){
                eColJoin = rJoin.getValues().get(relation.getColonneIndex(vVal));
            }
            else{
                eColJoin = rJoin.getValues().get(relationJoin.getColonneIndex(vVal));
            }
            
            if(relation.getColonneType(vCol)==null){
                vCol = vVal;
            }

            if(relation.getColonneType(vCol).startsWith("VARCHAR")){
                return executeVARCHAR(eCol, eColJoin);
            }
            if(relation.getColonneType(vCol).equals("INTEGER")){
                int eColInt = Integer.parseInt(eCol);
                int eColJoinInt = Integer.parseInt(eColJoin);
                return executeINTEGER(eColInt, eColJoinInt);
            }
            if(relation.getColonneType(vCol).equals("REAL")){
                double eColDouble = Double.parseDouble(eCol);
                double eColJoinDouble = Double.parseDouble(eColJoin);
                return executeREAL(eColDouble, eColJoinDouble);
            }
            return false;
        }
        

        private boolean executeREAL(double eColDouble, double eColJoinDouble) {

            if(op.equals("=")){
                return eColDouble == eColJoinDouble;
            }
            if(op.equals("<>")){
                return eColDouble != eColJoinDouble;
            }
            if(op.equals(">")){
                return eColDouble > eColJoinDouble;
            }
            if(op.equals("<")){
                return eColDouble < eColJoinDouble;
            }
            if(op.equals(">=")){
                return eColDouble >= eColJoinDouble;
            }
            if(op.equals("<=")){
                return eColDouble <= eColJoinDouble;
            }
            return false;

        }

        private boolean executeINTEGER(int eColInt, int eColJoinInt) {
            if(op.equals("=")){
                return eColInt == eColJoinInt;
            }
            if(op.equals("<>")){
                return eColInt != eColJoinInt;
            }
            if(op.equals(">")){
                return eColInt > eColJoinInt;
            }
            if(op.equals("<")){
                return eColInt < eColJoinInt;
            }
            if(op.equals(">=")){
                return eColInt >= eColJoinInt;
            }
            if(op.equals("<=")){
                return eColInt <= eColJoinInt;
            }
            return false;
        }

        private boolean executeVARCHAR(String eCol, String eColJoin) {
            if(op.equals("=")){
                return eCol.equals(eColJoin);
            }
            if(op.equals("<>")){
                return !eCol.equals(eColJoin);
            }
            int res = eCol.compareToIgnoreCase(eColJoin);
            if(op.equals(">")&&(res>0)){
            	return true;
            }
            if(op.equals("<")&&(res<0)){
            	return true;
            }
            if(op.equals(">=")&&(res>=0)){
                return true;
            }
            if(op.equals("<=")&&(res<=0)){
                return true;
            }
            return false;
        }

        public String toString() {
            return col + " " + op + " " + val;
        }
    }
}

    
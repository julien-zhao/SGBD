import java.io.IOException;
import java.util.Vector;

public class InsertIntoCommand extends XCommand{
    private String nomRelation;
    private Vector<String> values;

    public InsertIntoCommand(String command) {
        values = new Vector<String>();
        String[] tokens = command.split(" ");
        nomRelation = tokens[2];
        String[] vals = tokens[4].split(",");

        vals[0] = vals[0].substring(1, vals[0].length());
        vals[vals.length - 1] = vals[vals.length - 1].substring(0, vals[vals.length - 1].length()-1 );

        for (int i = 0; i < vals.length; i++) {
            values.add(vals[i]);
        }
    }

    public void execute() {
       
        RelationInfo rel = Catalog.getSingleton().getRelationInfo(nomRelation);
        if (rel == null) {
            System.out.println("Relation " + nomRelation + " does not exist");
            return;
        }
        for(String v:values) {
        	System.out.println(v);
        }
        System.out.println(values.size()+" "+rel.getSize());
        if (values.size() != rel.getSize()) {
            System.out.println("Wrong number of values");
            return;
        }
        for(int i = 0; i < values.size(); i++) {
            String type = rel.getTabInfo().get(i).getType();
            if (type.equals("INTEGER")) {
                try {
                    Integer.parseInt(values.get(i));
                } catch (NumberFormatException e) {
                    System.out.println("Wrong type for value " + values.get(i));
                    return;
                }
            } else if (type.equals("REAL")) {
                try {
                    Float.parseFloat(values.get(i));
                } catch (NumberFormatException e) {
                    System.out.println("Wrong type for value " + values.get(i));
                    return;
                }
            } else if (type.startsWith("VARCHAR")) {
                if (values.get(i).length() > Integer.parseInt(type.substring(8, type.length() - 1))) {
                    System.out.println("Wrong type for value " + values.get(i));
                    return;
                }
            }
        }

        Record rec = new Record(rel);
        rec.addTuple(values);
        try {
            FileManager.getSingleton().insertRecordIntoRelation(rec);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("Insertion reussie");

    }


    
    public String getNomRelation() {
    	return nomRelation;
    }
    
    public void setNomRelation(String nomRelation) {
    	this.nomRelation = nomRelation;
    }
}
    
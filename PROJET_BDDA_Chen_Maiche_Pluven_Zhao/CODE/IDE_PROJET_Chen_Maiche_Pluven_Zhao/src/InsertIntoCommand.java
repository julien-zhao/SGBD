import java.util.Vector;

public class InsertIntoCommand extends XCommand{
    private String nomRelation;
    private Vector<String> values;

    public InsertIntoCommand(String command) {
        values = new Vector<String>();
        String[] tokens = command.split(" ");
        nomRelation = tokens[2];
        String[] vals = tokens[3].split(",");

        vals[0] = vals[0].substring(1, vals[0].length());
        vals[vals.length - 1] = vals[vals.length - 1].substring(0, vals[vals.length - 1].length() - 1);

        for (int i = 0; i < vals.length; i++) {
            values.add(vals[i]);
        }
    }

    public void execute() {
        //TODO
    }


    
    public String getNomRelation() {
    	return nomRelation;
    }
    
    public void setNomRelation(String nomRelation) {
    	this.nomRelation = nomRelation;
    }
}
    
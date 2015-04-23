import java.util.ArrayList;

public class DBRecord {
    private ArrayList<String> columnValues;
    private String key;

    public DBRecord(ArrayList<String> columnValues, String key) {
        this.columnValues = columnValues;
        this.key = key;
    }

    public ArrayList<String> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(ArrayList<String> columnValues) {
        this.columnValues = columnValues;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString(){
        String s = "";
        for (String val: columnValues){
            s += val + "\t";
        }
        return s;
    }
}

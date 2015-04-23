import java.util.ArrayList;

public class DBTable {
    private String tableName;
    private ArrayList<String> columns;
    private ArrayList<DBRecord> data;

    public DBTable(String tableName, ArrayList<String> columns) {
        super();
        this.tableName = tableName;
        this.columns = columns;
        this.data = new ArrayList<DBRecord>();
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    public ArrayList<DBRecord> getData() {
        return data;
    }

    public void setData(ArrayList<DBRecord> data) {
        this.data = data;
    }

    public void addRecord(DBRecord dbr){
        this.data.add(dbr);
    }
    
    public String getTableName() {
        return tableName;
    }
}
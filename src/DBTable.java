import java.util.ArrayList;

public class DBTable {
    private String tableName;
    private ArrayList<String> columns;
    private ArrayList<DBRecord> records;
    private int pk;

    public DBTable(String tableName, ArrayList<String> columns) {
        super();
        this.tableName = tableName;
        this.columns = columns;
        this.records = new ArrayList<DBRecord>();
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    public ArrayList<DBRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<DBRecord> records) {
        this.records = records;
    }

    public void addRecord(DBRecord dbr){
        this.records.add(dbr);
    }

    public String getTableName() {
        return tableName;
    }

    public void setPK(String pk){
        for (int i = 0; i < columns.size(); i++){
            if (columns.get(i).equals(pk)){
                this.pk = i;
                break;
            }
        }
    }

    public int getPK(){
        return this.pk;
    }
}
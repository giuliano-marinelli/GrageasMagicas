package db;

//public class DBEjemploSQLite extends SQLiteOpenHelper {

public class DBEjemploSQLite{
//make some variables

    /*
    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_NAME ="products.db";

    public static final String TABLE_PRODUCTS="products";

    public static final String COLOUMN_ID="_id";

    public static final String COLOUMN_PRODUCTNAME="productname";

    public DBEjemploSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS"+TABLE_PRODUCTS);

        onCreate(db);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {


        String query = "CREATE TABLE Products (productname varchar(255));";


        String query2="INSERT INTO Products (productname) VALUES ('queso');";


        db.execSQL(query);
        db.execSQL(query2);

    }

    public void addProduct(String products)

    {

        ContentValues values = new ContentValues();

        values.put(COLOUMN_PRODUCTNAME,products.get_productname());

        SQLiteDatabase db= getWritableDatabase();

        db.insert(TABLE_PRODUCTS,null,values);

        db.close();

    }

    public void deleteProduct(String productname){

        SQLiteDatabase db =getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLOUMN_PRODUCTNAME + "=\"" + productname + "\";");

    }

    public String databasetostring(){

        String dbString="";

        SQLiteDatabase db= getWritableDatabase();

        dbString= getDatabaseName();

        String query = "SELECT * FROM Products";

        Cursor c =db.rawQuery(query,null);


        c.moveToFirst();

        //dbString= c.getColumnName(0);

        while (!c.isAfterLast())

        {

            if(c.getString(c.getColumnIndex("productname"))!=null)

            {

                dbString+= c.getString(c.getColumnIndex("productname"));

                dbString+="\n";

            }

            c.moveToNext();

        }


        db.close();


        return dbString;

    }
    */

}
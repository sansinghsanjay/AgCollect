package com.example.root.jiocollect;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // vars
    Context context;
    String tableName[];
    String columnName[];

    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int version) {
        // init databaseName
        super(context, databaseName, factory, version);
        // init vars
        this.context = context;
        tableName = new String[] {"crop_table", "season_table", "stage_table", "healthStatus_table",
                    "pestAttack_table", "disease_table", "nutrientDeficiency_table", "weedIntensity_table",
                    "waterStress_table"};
        columnName = new String[] {"item", "id"};
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating table
        for(int i = 0; i < tableName.length; i++) {
            db.execSQL("create table " + tableName[i] + " (" + columnName[0] + " text unique not null, "
                    + columnName[1] + " text primary key);");
        }

        // inserting static values into crop_table
        db.execSQL("insert into " + tableName[0] + " values ('rice', 'C1');");
        db.execSQL("insert into " + tableName[0] + " values ('cotton', 'C2');");
        db.execSQL("insert into " + tableName[0] + " values ('wheat', 'C3');");
        db.execSQL("insert into " + tableName[0] + " values ('maize', 'C4');");
        db.execSQL("insert into " + tableName[0] + " values ('redgram', 'C5');");
        db.execSQL("insert into " + tableName[0] + " values ('soyabean', 'C6');");
        db.execSQL("insert into " + tableName[0] + " values ('groundnut', 'C7');");
        db.execSQL("insert into " + tableName[0] + " values ('blackgram', 'C8');");

        // inserting static values into season table
        db.execSQL("insert into " + tableName[1] + " values ('earlykharif', 'SS1');");
        db.execSQL("insert into " + tableName[1] + " values ('kharif', 'SS2');");
        db.execSQL("insert into " + tableName[1] + " values ('latekharif', 'SS3');");
        db.execSQL("insert into " + tableName[1] + " values ('earlyrabi', 'SS4');");
        db.execSQL("insert into " + tableName[1] + " values ('rabi', 'SS5');");
        db.execSQL("insert into " + tableName[1] + " values ('earlysummer', 'SS6');");
        db.execSQL("insert into " + tableName[1] + " values ('summer', 'SS7');");

        // inserting static values into stage table
        db.execSQL("insert into " + tableName[2] + " values ('nursery', 'ST1');");
        db.execSQL("insert into " + tableName[2] + " values ('earlyvegetative', 'ST2');");
        db.execSQL("insert into " + tableName[2] + " values ('tillering', 'ST3');");
        db.execSQL("insert into " + tableName[2] + " values ('doughmilking', 'ST4');");
        db.execSQL("insert into " + tableName[2] + " values ('grainfilling', 'ST5');");
        db.execSQL("insert into " + tableName[2] + " values ('earlymaturity', 'ST6');");
        db.execSQL("insert into " + tableName[2] + " values ('latematurity', 'ST7');");
        db.execSQL("insert into " + tableName[2] + " values ('vegetative', 'ST8');");
        db.execSQL("insert into " + tableName[2] + " values ('squaring', 'ST9');");
        db.execSQL("insert into " + tableName[2] + " values ('flowering', 'ST10');");
        db.execSQL("insert into " + tableName[2] + " values ('bollformation', 'ST11');");
        db.execSQL("insert into " + tableName[2] + " values ('bollmaturing', 'ST12');");
        db.execSQL("insert into " + tableName[2] + " values ('whorlformation', 'ST13');");
        db.execSQL("insert into " + tableName[2] + " values ('tasselling', 'ST14');");
        db.execSQL("insert into " + tableName[2] + " values ('silking', 'ST15');");
        db.execSQL("insert into " + tableName[2] + " values ('milkystage', 'ST16');");
        db.execSQL("insert into " + tableName[2] + " values ('grainmaturing', 'ST17');");
        db.execSQL("insert into " + tableName[2] + " values ('podformation', 'ST18');");
        db.execSQL("insert into " + tableName[2] + " values ('podmaturation', 'ST19');");
        db.execSQL("insert into " + tableName[2] + " values ('pegpenetration', 'ST20');");

        // inserting static values into healthStatus_table
        db.execSQL("insert into " + tableName[3] + " values ('healthy', 'H1');");
        db.execSQL("insert into " + tableName[3] + " values ('unhealthy', 'H2');");

        // inserting static values into pestAttack_table
        db.execSQL("insert into " + tableName[4] + " values ('americanpotborer', 'P1');");
        db.execSQL("insert into " + tableName[4] + " values ('aphid', 'P2');");
        db.execSQL("insert into " + tableName[4] + " values ('armyworm', 'P3');");
        db.execSQL("insert into " + tableName[4] + " values ('biharhairycaterpillar', 'P4');");
        db.execSQL("insert into " + tableName[4] + " values ('blisterbeetles', 'P5');");
        db.execSQL("insert into " + tableName[4] + " values ('bluebutterflypodborer', 'P6');");
        db.execSQL("insert into " + tableName[4] + " values ('brownmite', 'P7');");
        db.execSQL("insert into " + tableName[4] + " values ('brownplanthopperwhitebackedplanthopper', 'P8');");
        db.execSQL("insert into " + tableName[4] + " values ('caseworm', 'P9');");
        db.execSQL("insert into " + tableName[4] + " values ('cobborer', 'P10');");
        db.execSQL("insert into " + tableName[4] + " values ('cutworm', 'P11');");
        db.execSQL("insert into " + tableName[4] + " values ('duskycottonbugs', 'P12');");
        db.execSQL("insert into " + tableName[4] + " values ('eriophyidmite', 'P13');");
        db.execSQL("insert into " + tableName[4] + " values ('fruitbollborer', 'P14');");
        db.execSQL("insert into " + tableName[4] + " values ('gallmidge', 'P15');");
        db.execSQL("insert into " + tableName[4] + " values ('grampodborer', 'P16');");
        db.execSQL("insert into " + tableName[4] + " values ('greenleafhopper', 'P17');");
        db.execSQL("insert into " + tableName[4] + " values ('gridlebeetle', 'P18');");
        db.execSQL("insert into " + tableName[4] + " values ('jassids', 'P19');");
        db.execSQL("insert into " + tableName[4] + " values ('leafdefoliator', 'P20');");
        db.execSQL("insert into " + tableName[4] + " values ('leaffolder', 'P21');");
        db.execSQL("insert into " + tableName[4] + " values ('leafhopper', 'P22');");
        db.execSQL("insert into " + tableName[4] + " values ('leafminer', 'P23');");
        db.execSQL("insert into " + tableName[4] + " values ('mealybugs', 'P24');");
        db.execSQL("insert into " + tableName[4] + " values ('noinsect', 'P25');");
        db.execSQL("insert into " + tableName[4] + " values ('otherinsect', 'P26');");
        db.execSQL("insert into " + tableName[4] + " values ('pinkbollworm', 'P27');");
        db.execSQL("insert into " + tableName[4] + " values ('pinkstemborer', 'P28');");
        db.execSQL("insert into " + tableName[4] + " values ('podborer', 'P29');");
        db.execSQL("insert into " + tableName[4] + " values ('podbugs', 'P30');");
        db.execSQL("insert into " + tableName[4] + " values ('podfly', 'P31');");
        db.execSQL("insert into " + tableName[4] + " values ('redhairycaterpillar', 'P32');");
        db.execSQL("insert into " + tableName[4] + " values ('redgramplummoth', 'P33');");
        db.execSQL("insert into " + tableName[4] + " values ('redgrampodfly', 'P34');");
        db.execSQL("insert into " + tableName[4] + " values ('ricehispa', 'P35');");
        db.execSQL("insert into " + tableName[4] + " values ('semilopper', 'P36');");
        db.execSQL("insert into " + tableName[4] + " values ('shootfly', 'P37');");
        db.execSQL("insert into " + tableName[4] + " values ('spottedbollworm', 'P38');");
        db.execSQL("insert into " + tableName[4] + " values ('spottedpodborer', 'P39');");
        db.execSQL("insert into " + tableName[4] + " values ('stemfly', 'P40');");
        db.execSQL("insert into " + tableName[4] + " values ('stemborer', 'P41');");
        db.execSQL("insert into " + tableName[4] + " values ('stinkbugs', 'P42');");
        db.execSQL("insert into " + tableName[4] + " values ('termites', 'P43');");
        db.execSQL("insert into " + tableName[4] + " values ('thrips', 'P44');");
        db.execSQL("insert into " + tableName[4] + " values ('tobaccocaterpillar', 'P45');");
        db.execSQL("insert into " + tableName[4] + " values ('unknown', 'P46');");
        db.execSQL("insert into " + tableName[4] + " values ('whitefly', 'P47');");

        // inserting static values into disease_table
        db.execSQL("insert into " + tableName[5] + " values ('alternariablight', 'D1');");
        db.execSQL("insert into " + tableName[5] + " values ('alternarialeafblight', 'D2');");
        db.execSQL("insert into " + tableName[5] + " values ('alternarialeafspot', 'D3');");
        db.execSQL("insert into " + tableName[5] + " values ('anthracnose', 'D4');");
        db.execSQL("insert into " + tableName[5] + " values ('bacterialblight', 'D5');");
        db.execSQL("insert into " + tableName[5] + " values ('bacterialblightangularleafspotofcotton', 'D6');");
        db.execSQL("insert into " + tableName[5] + " values ('bacterialleafblight', 'D7');");
        db.execSQL("insert into " + tableName[5] + " values ('bandedleafandsheathblight', 'D8');");
        db.execSQL("insert into " + tableName[5] + " values ('blackrust', 'D9');");
        db.execSQL("insert into " + tableName[5] + " values ('brownspot', 'D10');");
        db.execSQL("insert into " + tableName[5] + " values ('brownrust', 'D11');");
        db.execSQL("insert into " + tableName[5] + " values ('cercosporaleafblight', 'D12');");
        db.execSQL("insert into " + tableName[5] + " values ('charcoalrot', 'D13');");
        db.execSQL("insert into " + tableName[5] + " values ('collarrot', 'D14');");
        db.execSQL("insert into " + tableName[5] + " values ('collarrotsclerotiumblight', 'D15');");
        db.execSQL("insert into " + tableName[5] + " values ('cottonleafcurlvirus', 'D16');");
        db.execSQL("insert into " + tableName[5] + " values ('downymildew', 'D17');");
        db.execSQL("insert into " + tableName[5] + " values ('dryrootrot', 'D18');");
        db.execSQL("insert into " + tableName[5] + " values ('footrot', 'D19');");
        db.execSQL("insert into " + tableName[5] + " values ('fusariumstalkrot', 'D20');");
        db.execSQL("insert into " + tableName[5] + " values ('fusariumwilt', 'D21');");
        db.execSQL("insert into " + tableName[5] + " values ('greymildew', 'D22');");
        db.execSQL("insert into " + tableName[5] + " values ('karnalblunt', 'D23');");
        db.execSQL("insert into " + tableName[5] + " values ('leafblast', 'D24');");
        db.execSQL("insert into " + tableName[5] + " values ('leafcrinkle', 'D25');");
        db.execSQL("insert into " + tableName[5] + " values ('leafspot', 'D26');");
        db.execSQL("insert into " + tableName[5] + " values ('loosesmut', 'D27');");
        db.execSQL("insert into " + tableName[5] + " values ('macrophomniablight', 'D28');");
        db.execSQL("insert into " + tableName[5] + " values ('maydisleafblight', 'D29');");
        db.execSQL("insert into " + tableName[5] + " values ('myrotheciumleafspot', 'D30');");
        db.execSQL("insert into " + tableName[5] + " values ('neckblast', 'D31');");
        db.execSQL("insert into " + tableName[5] + " values ('nodisease', 'D32');");
        db.execSQL("insert into " + tableName[5] + " values ('otherdisease', 'D33');");
        db.execSQL("insert into " + tableName[5] + " values ('peanutbudnecrosistospovirusdisease', 'D34');");
        db.execSQL("insert into " + tableName[5] + " values ('polysorarust', 'D35');");
        db.execSQL("insert into " + tableName[5] + " values ('powderymildew', 'D36');");
        db.execSQL("insert into " + tableName[5] + " values ('redgramyellowmosaic', 'D37');");
        db.execSQL("insert into " + tableName[5] + " values ('rhizoctoniarootrot', 'D38');");
        db.execSQL("insert into " + tableName[5] + " values ('rootrotandleafblight', 'D39');");
        db.execSQL("insert into " + tableName[5] + " values ('rootrot', 'D40');");
        db.execSQL("insert into " + tableName[5] + " values ('rust', 'D41');");
        db.execSQL("insert into " + tableName[5] + " values ('sheathblight', 'D42');");
        db.execSQL("insert into " + tableName[5] + " values ('stemrot', 'D43');");
        db.execSQL("insert into " + tableName[5] + " values ('sterilitymosaic', 'D44');");
        db.execSQL("insert into " + tableName[5] + " values ('tungrodisease', 'D45');");
        db.execSQL("insert into " + tableName[5] + " values ('turcicumleafblight', 'D46');");
        db.execSQL("insert into " + tableName[5] + " values ('unknown', 'D47');");
        db.execSQL("insert into " + tableName[5] + " values ('wilt', 'D48');");
        db.execSQL("insert into " + tableName[5] + " values ('yellowmosaicvirus', 'D49');");
        db.execSQL("insert into " + tableName[5] + " values ('yellowstriperust', 'D50');");

        // inserting static values into nutrientDeficiency_table
        db.execSQL("insert into " + tableName[6] + " values ('boron', 'N1');");
        db.execSQL("insert into " + tableName[6] + " values ('calcium', 'N2');");
        db.execSQL("insert into " + tableName[6] + " values ('iron', 'N3');");
        db.execSQL("insert into " + tableName[6] + " values ('magnesium', 'N4');");
        db.execSQL("insert into " + tableName[6] + " values ('nodeficiency', 'N5');");
        db.execSQL("insert into " + tableName[6] + " values ('otherdeficiency', 'N6');");
        db.execSQL("insert into " + tableName[6] + " values ('potash', 'N7');");
        db.execSQL("insert into " + tableName[6] + " values ('potassium', 'N8');");
        db.execSQL("insert into " + tableName[6] + " values ('sulphur', 'N9');");
        db.execSQL("insert into " + tableName[6] + " values ('unknown', 'N10');");
        db.execSQL("insert into " + tableName[6] + " values ('zinc', 'N11');");

        // inserting static values into weedIntensity_table
        db.execSQL("insert into " + tableName[7] + " values ('weedfree', 'WI1');");
        db.execSQL("insert into " + tableName[7] + " values ('low', 'WI2');");
        db.execSQL("insert into " + tableName[7] + " values ('medium', 'WI3');");
        db.execSQL("insert into " + tableName[7] + " values ('high', 'WI4');");
        db.execSQL("insert into " + tableName[7] + " values ('others', 'WI5');");

        // inserting static values into waterStress_table
        db.execSQL("insert into " + tableName[8] + " values ('nostress', 'WS1');");
        db.execSQL("insert into " + tableName[8] + " values ('mild', 'WS2');");
        db.execSQL("insert into " + tableName[8] + " values ('medium', 'WS3');");
        db.execSQL("insert into " + tableName[8] + " values ('high', 'WS4');");
        db.execSQL("insert into " + tableName[8] + " values ('others', 'WS5');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // delete current database and create updated one
        if(oldVersion < 2) {
            for (int i = 0; i < tableName.length; i++) {
                db.execSQL("drop table if exists + " + tableName[i] + ";");
            }
            onCreate(db);
        }
    }

    public String getCode(String table_name, String value) {
        String id = "NA";
        // get database
        SQLiteDatabase db = this.getReadableDatabase();
        // extract code for passed value from database
        Cursor result = db.rawQuery("select " + columnName[1] + " from " + table_name + " where " + columnName[0] + "='" + value + "';", null);
        while(result.moveToNext()) {
            int tempIndex = result.getColumnIndex(columnName[1]);
            id = result.getString(tempIndex);
        }
        return id;
    }

}
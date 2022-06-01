
public class SQLManager {

    public static void createTables(){
        System.out.println("Dropping tables");
        MySQL.onUpdate("DROP TABLE IF EXISTS call_history");
        MySQL.onUpdate("DROP TABLE IF EXISTS person");
        MySQL.onUpdate("DROP TABLE IF EXISTS company");

        System.out.println("Tables dropped, creating new ones..");

        MySQL.onUpdate("CREATE TABLE IF NOT EXISTS test.person(\n" +
                "\tperson_id INT NOT NULL, \n" +
                "\tfirstname VARCHAR(15), \n" +
                "\tlastname VARCHAR(15), \n" +
                "\ttel_number VARCHAR(15), \n" +
                "\temail VARCHAR(50), \n" +
                "\tPRIMARY KEY (person_id));");

        MySQL.onUpdate("CREATE TABLE IF NOT EXISTS test.company(\n" +
                "\tcompany_id INT NOT NULL, \n" +
                "\tcompany_name VARCHAR(15), \n" +
                "\ttel_number VARCHAR(15), \n" +
                "\temail VARCHAR(50), \n" +
                "\tPRIMARY KEY (company_id) );");

        MySQL.onUpdate("CREATE TABLE IF NOT EXISTS test.call_history(\n" +
                "\tcall_history_id INT NOT NULL, \n" +
                "\tperson_id INT NOT NULL, \n" +
                "\tcompany_id INT NOT NULL, \n" +     // staviti da je caller_id = 1 uvijek
                "\tcall_started TIMESTAMP," +
                "\tPRIMARY KEY (call_history_id), \n" +
                "\tFOREIGN KEY (person_id) REFERENCES person (person_id), \n" +
                "\tFOREIGN KEY (company_id) REFERENCES company (company_id));");

        // pozivatelj ce zvati ili person ili company, znaci da imam caller_id i receiver_id


        System.out.println("Tables in database created.");
    }
}

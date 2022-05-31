package main.data;

public class SQLManager {

    public static void createTables(){
        System.out.println("Dropping tables");
        MySQL.onUpdate("DROP TABLE IF EXISTS call_history");
        MySQL.onUpdate("DROP TABLE IF EXISTS person");
        MySQL.onUpdate("DROP TABLE IF EXISTS company");

        System.out.println("Tables dropped, creating new ones..");
        MySQL.onUpdate("CREATE TABLE IF NOT EXISTS test.person(\n" +
                "\tperson_id INT NOT NULL, \n" +
                "\tPRIMARY KEY (person_id), \n" +
                "\tfirstname VARCHAR(15), \n" +
                "\tlastname VARCHAR(15), \n" +
                "\ttel_number VARCHAR(15), \n" +
                "\temail VARCHAR(50));");

        MySQL.onUpdate("CREATE TABLE IF NOT EXISTS test.company(\n" +
                "\tcompany_id INT NOT NULL, \n" +
                "\tPRIMARY KEY (company_id), \n" +
                "\tcompany_name VARCHAR(15), \n" +
                "\ttel_number VARCHAR(15), \n" +
                "\temail VARCHAR(50));");

        MySQL.onUpdate("CREATE TABLE IF NOT EXISTS call_history(\n" +
                "\tcall_history_id INT NOT NULL, \n" +
                "\tperson_id INT NOT NULL, \n" +
                "\tcompany_id INT NOT NULL, \n" +
                "\tPRIMARY KEY (call_history_id), \n" +
                "\tFOREIGN KEY (person_id) REFERENCES person(person_id), \n" +
                "\tFOREIGN KEY (company_id) REFERENCES company(company_id), \n" +
                "\tcall_date DATE);");

        System.out.println("Tables in database created.");
    }
}

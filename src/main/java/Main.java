import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {

    private static final Scanner in = new Scanner(System.in);  //Pitanje: sto znaci warning 'Field 'in'/'people' may be final
    private static List<EntitySuper> people = new ArrayList<>();
    private static List<EntitySuper> history = new ArrayList<>();
    private static int counter = 0;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001b[36m";

    private static void printMenu() {
        System.out.println(ANSI_YELLOW + "What do you want to do? Enter the option number.");
        System.out.println("1. Show all contacts");
        System.out.println("2. Add a new contact");
        System.out.println("3. Edit contact");
        System.out.println("4. Delete contact");
        System.out.println("5. Make a call" + ANSI_RESET);
    }

    public static void main(String[] args) throws SQLException {

//       KONEKCIJA NA MySQL

       //var con = MySQL.getConnection();
        SQLManager.createTables();

        Person per = new Person("+385995021254", "ivana.bubalo2@gmail.com", "Ivana", "Bubalo");
        MySQL.insertPerson(per);
        System.out.println("You have successfully added a contact " + per);

        Company com = new Company("+385994567898", "koios@hr", "koios");
        MySQL.insertCompany(com);
        System.out.println("You have successfully added a contact" + com);

        updateList();

        int choice;
        do {
            printMenu();
            choice = Integer.parseInt(in.nextLine());

            if (choice == 1) {
                izlistaj();
            } else if (choice == 2) {
                dodaj();
            } else if (choice == 3) {
                edit();
            } else if (choice == 4) {
                delete();
            } else if (choice == 5){
                call();
            } else {
                System.out.println("Unknown command entered, try again");
            }
        }
        while (choice < 7);

    }

    private static void updateList() throws SQLException {
        List<EntitySuper> listPerson = MySQL.selectPerson();
        List<EntitySuper> listCompany = MySQL.selectCompany();
        List<EntitySuper> listOfAll = new ArrayList<>(listCompany);
        listOfAll.addAll(listPerson);
        people = listOfAll;
    }


    private static void izlistaj() throws SQLException {
        updateList();
        for (EntitySuper var : people) {
            System.out.println(var);
        }
    }


    private static String brojMobitela() {
        String broj, pozivni;
        do {
            System.out.println("Enter the number in the format +385XXXXXXXXX and up to 13 characters long:");
            broj = in.nextLine();   // stavit uvjet da broj ima minimalno 4 znaka, u protivnom napuni pozivni sa ...
            if (broj.length() < 4) {           //TVOJA IDEJA
                pozivni = "";
            } else {
                pozivni = broj.substring(0, 4);
            }
        }
        while (broj.length() != 13 || !pozivni.equals("+385"));
        return broj;
    }

    private static void dodajTvrtku() {
        System.out.println("Please provide your business name and cell phone number below:\n Name: ");

        //        MySQL.onUpdate("ALTER TABLE test.company AUTO_INCREMENT (company_id)"); // provjeri jel mi ovo treba
        String name = in.nextLine();
        String tel_number = brojMobitela();
        System.out.println("Email: ");
        String email = in.nextLine();

        Company com = new Company(tel_number, email, name);
        MySQL.insertCompany(com);

        System.out.println("You have successfully added a contact " + name);
        try {
            updateList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void dodajOsobu() {

        System.out.println("Please provide first and last name and cell phone number below: \n First name: ");
        String name = in.nextLine();
        System.out.println("Last name: ");
        String surname = in.nextLine();
        String tel_number = brojMobitela();  //TODO: napravila
        System.out.println("Email: ");
        String email = in.nextLine();

        Person per = new Person(tel_number, email, name, surname);
        MySQL.insertPerson(per);
        System.out.println("You have successfully added a contact" + name);

        try {
            updateList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void dodaj() {

        System.out.println(ANSI_YELLOW + "Want to add a person or company? Select option number: " + ANSI_RESET);
        System.out.println("1. Add person\n" + "2. Add company");
        int choice = Integer.parseInt(in.nextLine());

        if (choice == 1) dodajOsobu();
        else if (choice == 2) dodajTvrtku();
        else printMenu();
    }


    //todo:    metoda za pozivanje; kad se izvrši call (id 1 zove nekog id-a, upisati to u history) + timestamp i trajanje poziva

    private static void call(){
        System.out.println("Who do you want to call? Enter the id: ");  // pozivatelj ima id = 1, receiver je neki idući
        int oznaka;
        try {
            oznaka = Integer.parseInt(in.nextLine());
            System.out.println(ANSI_CYAN +"Calling.. " + oznaka + ANSI_RESET);


        } catch(Exception e){
            System.out.println("Invalid input, please try again: ");
            oznaka = Integer.parseInt(in.nextLine());
        }
        for (EntitySuper p : people){
            if (p.getId() == oznaka){
                //System.out.println("Calling  " + p); // todo:  PROMJENA iz 'p' u 'p.getTel'

                //todo sad updejtaj tablicu da se radi call

                if (p instanceof Person) {
                    MySQL.updateHistoryPerson((Person) p);
                } else {
                    MySQL.updateHistoryCompany((Company) p);
                }
            }
        }

       // History history = new History();
        //MySQL.insertCallHistory(history);
      //  counter++;
    }

// TODO: PREVESTI NA ENG

    private static void edit() {

        System.out.println("Enter contact ID: ");
        int oznaka;
        try {
            oznaka = Integer.parseInt(in.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid entry, try again");
            oznaka = Integer.parseInt(in.nextLine());
        }
        // String getIdToEdit = String.valueOf(Integer.parseInt(in.nextLine()));

        for (EntitySuper p : people) {      //people je lista imenika, 'p' je cijeli redak (objekt) u listi; u isti imenik trpam firme i ljude
            if (p.getId() == oznaka) {

                System.out.println("The person with that ID is " + p); //hocu da mi prvo ispise kontakt s tim ID-em, a onda mijenja njegove vrijednosti

                System.out.println("Edit first name, last name or phone number. " +
                        "If you do not want to change any data, press Enter.");

                if (p instanceof Company) {    //todo:  OVDJE SAM STALA!
                    System.out.println("New company name: ");
                    String new_comname = in.nextLine();
                    if (!new_comname.equals("")) {
                        ((Company) p).setCompanyName(new_comname);

                    }
                    MySQL.updateCompany((Company) p);
                }

                if (p instanceof Person) {
                    System.out.println("New name: ");
                    String new_firstname = in.nextLine();
                    if (!new_firstname.equals("")) {
                        ((Person) p).setFirstname(new_firstname);
                    }


                    System.out.println("Novo prezime: ");
                    String new_lastname = in.nextLine();
                    if (!new_lastname.equals("")) {
                        ((Person) p).setLastname(new_lastname);
                    }
                    MySQL.updatePerson((Person) p);
                    //dodala, drugi pokusajMySQL.updatePerson((Person) p);  //dodala, drugi pokusaj
                }

                System.out.println("New phone number");
                if (!in.nextLine().equals("")) {
                    p.setTel_number(brojMobitela());
                }

                System.out.println("New email: ");
                String new_email = in.nextLine();
                if (!new_email.equals("")) {
                    p.setEmail(new_email);
                }
                System.out.println(p);
                // ovo ispod sam zakomentirala jer nije radila edit metoda.
                // pokusat cu updatePerson direktno ispod Persona, updateCompany ispod Company-a
            }

            if (p instanceof Person) {
                MySQL.updatePerson((Person) p);
            } else {
                MySQL.updateCompany((Company) p);
            } //todo: nešto ne valja, popravi


            try {
                updateList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void delete() {
        System.out.println("Upišite oznaku kontakta kojeg zelite izbrisat. ");
        String s = in.nextLine();

        int idToDelete = 0;
        try {
            idToDelete = Integer.parseInt(s);
        } catch (Exception e) {
            //   System.out.println("Unesite broj");   ovo je višak
        }

        EntitySuper toDelete = null;
        for (EntitySuper p : people) {

            if (p.getId() == idToDelete) {
                toDelete = p;
                break;
            }
        }
        if (toDelete instanceof Person) {
            MySQL.deletePerson((Person) toDelete);
        } else {
            MySQL.deleteCompany((Company) toDelete);
        }
        people.remove(toDelete);


    }


}
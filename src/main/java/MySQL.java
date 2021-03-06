
import com.sun.jdi.IntegerValue;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.Instant;

public class MySQL {

    private static Connection connection;
    private static Statement statement;

    public static final String deleteCompany = "DELETE FROM company WHERE company_id = ? ";
    public static final String deletePerson = "DELETE FROM person WHERE person_id = ? ";

    public static final String insertCompanyStatement = "INSERT INTO company (company_id, company_name, tel_number, email) VALUES(?,?,?,?)";
    public static final String insertPersonStatement = "INSERT INTO person (person_id, firstname, lastname, tel_number, email) VALUES(?,?,?,?,?)";  //TODO: promijeni redosljed kljuceva da bude kao u SQL manageru

    public static final String selectCompany = "SELECT * FROM company";
    public static final String selectPerson = "SELECT * FROM person";

    public static final String updateCompany = "UPDATE company SET company_name=?, tel_number=?, email=? WHERE company_id = ?";
    public static final String updatePerson = "UPDATE person SET firstname=?, lastname=?, tel_number=?, email=? WHERE person_id = ?";

    public static final String updateHistoryCompany = "INSERT INTO call_history ( company_id, call_started) VALUES(?,?)";
    public static final String updateHistoryPerson = "INSERT INTO call_history ( person_id, call_started) VALUES(?,?)";

//    public static final String updateHistoryCompany = "INSERT INTO call_history (company_id, call_started) VALUES(?,?)";
//    public static final String updateHistoryPerson = "INSERT INTO call_history (person_id, call_started) VALUES(?,?)";

    public static final String updateHistory = "UPDATE call_history";
    public static final String selectHistory = "SELECT * FROM call_history";

//    public static final String updateHistoryCompany = "INSERT INTO call_history (company_id, call_started) AND UPDATE TABLE";
//    public static final String updateHistoryPerson = "INSERT INTO call_history (person_id, call_started) AND UPDATE TABLE";


    public static boolean updateHistoryPerson(Person person) {


        try (CallableStatement st = connection.prepareCall(updateHistoryPerson)) {

            st.setInt(1, person.getId());
            st.setTimestamp(2, Timestamp.from(Instant.now()));
            st.executeUpdate();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean updateHistoryCompany(Company company) {
        try (CallableStatement st = connection.prepareCall(updateHistoryCompany)) {

            st.setInt(1, company.getId());
            st.setTimestamp(2, Timestamp.from(Instant.now()));

            st.executeUpdate();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static Statement getConnection() {
        connection = null;
        statement = null;

        try {
            // ako dobijem timezone error, sljede??i link treba naljepiti na ime baze ("test")
            // ?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
            //String url = "jdbc:mysql://localhost:3307/test?useUnicode=true%useJDBCCompliantTimezoneShift=true&useLegacyDateTimeCode=false&serverTimezone=UTC";

            String url = "jdbc:mysql://localhost:3307/test?serverTimezone=UTC";
            String username = "root";
            String password = "Melon123";

            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

            //System.out.println("Database now online ");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    public static void disconnect() {
        try {
            connection.close();
            System.out.println("Database now offline");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void onUpdate(String sql) {
        getConnection();
        try {
            statement.execute(sql);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static ResultSet onQuery(String sql) { //resultSet - from JavaSQL - znaci da ce output biti nesto iz nase odabrane baze
        try {
            return statement.executeQuery(sql);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static boolean insertCompany(Company company) {
        try (CallableStatement cstmt = connection.prepareCall(insertCompanyStatement)) {
            cstmt.setInt(1, company.getId());
            cstmt.setString(2, company.getCompanyName());
            cstmt.setString(3, company.getTel_number());
            cstmt.setString(4, company.getEmail());   // ?? u klasama Company i Person nemam gettere za tel_number, email, samo za imena
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insertPerson(Person person) {
        try (CallableStatement cstmt = connection.prepareCall(insertPersonStatement)) {
            cstmt.setInt(1, person.getId());
            cstmt.setString(2, person.getFirstname());
            cstmt.setString(3, person.getLastname());
            cstmt.setString(4, person.getTel_number());
            cstmt.setString(5, person.getEmail());
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static boolean insertCallHistory(){
//
//    }

    //todo: napraviti metodu za insertanje u tablicu call_history
    // napravit cu novu klasu s atributima

//    public static List<EntitySuper> selectHistory() throws SQLException {
//        List<EntitySuper> list = new ArrayList<>();
//        Statement connection = getConnection();
//        ResultSet resultSetHistory = null;
//        ResultSet resultSetCompany = null;
//        ResultSet resultPerson = null;
//
//        try {
//            resultSetHistory = connection.executeQuery(selectHistory);
//            //ResultSet resultSetCompany = connection.executeQuery(selectCompany);
//            //ResultSet resultPerson = connection.executeQuery(selectPerson);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
////        try {
////            resultSetCompany = connection.executeQuery(selectCompany);
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        try {
////            resultPerson = connection.executeQuery(selectPerson);
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
//
//        try {
//
//            if (!resultSetHistory.next()) {
//                System.out.println("There are no calls in history");
//            }else {
//                do {
//                    int id = resultSetHistory.getInt("call_history_id");
//                    int idp = resultSetPerson.getInt("person_id");
//                    int idc = resultSetCompany.getInt("company_id");
//
//                    EntitySuper hist = new EntitySuper(call_history_id, person_id, company_id, call_started);
//                    list.add(hist);
//                } while (resultSetHistory.next());
//            }
//            return list;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    public static List<EntitySuper> selectCompany() throws SQLException {
        List<EntitySuper> list = new ArrayList<>();
        Statement connection = getConnection();
        ResultSet resultSetCompany = connection.executeQuery(selectCompany);
        try {
            if (!resultSetCompany.next()) {   //ako ne postoji sljede??i objekt (tj prvi objekt, jer kre??emo od nule/ni??ega pa next)
                System.out.println("The 'company' directory is empty");
            } else {
                do {
                    int id = resultSetCompany.getInt("company_id");
                    String company_name = resultSetCompany.getString("company_name");
                    String email = resultSetCompany.getString("email");
                    String phone = resultSetCompany.getString("tel_number");

                    Company comp = new Company(id, phone, email, company_name);
                    list.add(comp);

                } while (resultSetCompany.next());
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static List<EntitySuper> selectHistory() throws SQLException {        // POCELA SAM PISATI ALI BIH TREBALA RADIT DRUGU KLASU PA NECU
//        List<EntitySuper> list = new ArrayList<>();
//        Statement connection = getConnection();
//        ResultSet resultSetHistory = connection.executeQuery(selectHistory);
//        try {
//            if (!resultSetHistory.next()) {
//                System.out.println("The 'history' directory is empty");
//            } else {
//                do {
//               //     int id = resultSetHistory.getInt("call_history_id");
//                    int person_id = resultSetHistory.getInt("person_id");
//                    int company_id = resultSetHistory.getInt("company_id");
//                    Timestamp call_started = resultSetHistory.getTimestamp("call_started");
//
//                    List<EntitySuper> listHistory = new EntitySuper(person_id, company_id, call_started);
//
//                    EntitySuper history = new EntitySuper(person_id, company_id, call_started);
//                    list.add(history);
//
//                } while (resultSetHistory.next());
//            }
//            return list;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public static List<EntitySuper> selectPerson() throws SQLException {
        List<EntitySuper> list = new ArrayList<>();
        Statement connection = getConnection();
        ResultSet resultSetPerson = connection.executeQuery(selectPerson);
        try {
            if (!resultSetPerson.next()) {   //ako ne postoji sljede??i objekt (tj prvi objekt, jer kre??emo od nule/ni??ega pa next)
                System.out.println("Imenik 'person' je prazan");      //todo: namjestiti metodu tako da povuce i ispise i podatke iz company i person
            } else {
                do {
                    int id = resultSetPerson.getInt("person_id");
                    String firstname = resultSetPerson.getString("firstname");
                    String lastname = resultSetPerson.getString("lastname");
                    String email = resultSetPerson.getString("email");
                    String phone = resultSetPerson.getString("tel_number");
                    Person per = new Person(id, phone, email, firstname, lastname);
                    list.add(per);
                } while (resultSetPerson.next());
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updatePerson(Person person) {
        try (CallableStatement st = connection.prepareCall(updatePerson)) {
            st.setInt(5, person.getId());
            st.setString(1, person.getFirstname());
            st.setString(2, person.getLastname());
            st.setString(3, person.getTel_number());
            st.setString(4, person.getEmail());

            st.execute();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean updateCompany(Company company) {
        try (CallableStatement st = connection.prepareCall(updateCompany)) {

            st.setInt(4, company.getId());
            st.setString(1, company.getCompanyName());
            st.setString(2, company.getTel_number());
            st.setString(3, company.getEmail());

            st.execute();
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean deletePerson(Person person) {
        try (CallableStatement st = connection.prepareCall(deletePerson)) {
            st.setInt(1, person.getId());
            st.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteCompany(Company company) {
        try (CallableStatement st = connection.prepareCall(deleteCompany)) {
            st.setInt(1, company.getId());
            st.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
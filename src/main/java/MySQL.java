import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySQL {

    private static Connection connection;
    private static Statement statement;

    public static final String insertCompanyStatement = "INSERT INTO company (company_id, company_name, tel_number, email) VALUES(?,?,?,?)";
    public static final String insertPersonStatement = "INSERT INTO person (person_id, firstname, lastname, tel_number, email) VALUES(?,?,?,?,?)";


    public static final String selectCompany = "SELECT * FROM company";
    public static final String selectPerson = "SELECT * FROM person";

    public static final String updateCompany = "UPDATE company SET company_name=?, tel_number=?, email=? WHERE company_id = ?";
    public static final String updatePerson = "UPDATE person SET firstname=?, lastname=?, tel_number=?, email=? WHERE person_id = ?";

    public static final String deleteCompany = "DELETE FROM company WHERE company_id = ? ";
    public static final String deletePerson = "DELETE FROM person WHERE person_id = ? ";


    public static Statement getConnection() {
        connection = null;
        statement = null;

        try {
            // ako dobijem timezone error, sljedeći link treba naljepiti na ime baze ("test")
            // ?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
            //String url = "jdbc:mysql://localhost:3307/test?useUnicode=true%useJDBCCompliantTimezoneShift=true&useLegacyDateTimeCode=false&serverTimezone=UTC";

            String url = "jdbc:mysql://localhost:3307/test?serverTimezone=UTC";
            String username = "root";
            String password = "Melon123";

            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

            System.out.println("Database now online ");

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
            cstmt.setString(4, company.getEmail());
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

    //todo: napraviti metodu za insertanje u tablicu call_history
    // napravit cu novu klasu s atributima

    //  public static

    public static List<EntitySuper> selectCompany() throws SQLException {
        List<EntitySuper> list = new ArrayList<>();
        Statement connection = getConnection();
        ResultSet resultSetCompany = connection.executeQuery(selectCompany);
        try {
            if (!resultSetCompany.next()) {   //ako ne postoji sljedeći objekt (tj prvi objekt, jer krećemo od nule/ničega pa next)
                System.out.println("Imenik 'company' je prazan");      //todo: namjestiti metodu tako da povuce i ispise i podatke iz company i person
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

    public static List<EntitySuper> selectPerson() throws SQLException {
        List<EntitySuper> list = new ArrayList<>();
        Statement connection = getConnection();
        ResultSet resultSetPerson = connection.executeQuery(selectPerson);
        try {
            if (!resultSetPerson.next()) {   //ako ne postoji sljedeći objekt (tj prvi objekt, jer krećemo od nule/ničega pa next)
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
            st.setInt(5,person.getId());
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
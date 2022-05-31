public class Person extends EntitySuper {
    private String firstname;
    private String lastname;

    public Person(String tel_number, String email, String firstname, String lastname) {
        super(tel_number, email);
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Person(int id, String tel_number, String email, String firstname, String lastname) {
        super(id, tel_number, email);
        this.firstname = firstname;
        this.lastname = lastname;
    }


    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + getId() + '\'' +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
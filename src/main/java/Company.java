public class Company extends EntitySuper {
    private String companyName;  // comname - ime firme

    public Company(String tel_number, String email, String companyName) {
        super(tel_number, email);
        this.companyName = companyName;
    }

    public Company(int id, String tel_number, String email, String companyName) {
        super(id, tel_number, email);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    @Override
    public String toString() {
        return "Company{" +
                "id='" + getId() + '\'' +
                "companyName='" + companyName + '\'' +
                '}';
    }
}
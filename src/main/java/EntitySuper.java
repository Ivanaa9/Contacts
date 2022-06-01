public abstract class EntitySuper {
    private int id;
    private String tel_number;
    private String email;
    private static int counter = 0;


    public String getTel_number() {return tel_number;}
    public void setTel_number(String tel_number) {
        this.tel_number = tel_number;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public int getId() {return id;}



    public EntitySuper(String tel_number, String email) {
        this.tel_number = tel_number;
        this.email = email;
        this.id = counter;
        counter++;
    }

    public EntitySuper(int id, String tel_number, String email){
        this.id = id;
        this.tel_number = tel_number;
        this.email = email;
        counter++;
    }

}
package main.model;

public class History extends EntitySuper {
    private int history_id;
    private static int counter = 0;



    public History(String tel_number, String email) {
        super(tel_number, email);
        counter++;
    }

    public History(int id, String tel_number, String email) {
        super(id, tel_number, email);
        counter++;
    }

    @Override
    public String toString() {
        return "History{" +
                "history_id=" + history_id +
                '}';
    }
}
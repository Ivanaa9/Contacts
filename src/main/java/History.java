//import java.sql.Timestamp;
//import java.time.Instant;
//
//public class History extends EntitySuper {
//
//    private int call_history_id;
//    private static int counter = 0;
//
//    public History(String tel_number, String email) {
//        super(tel_number, email);
//    }
//
//    public History(int id, String tel_number, String email) {
//        super(id, tel_number, email);
//    }
//
//
//
//    public History(int call_history_id, int person_id, int company_id, Timestamp call_started){
//        this.call_started = call_started;
////        this.person_id =
//
//    // todo: how to use/access variable from another class in constructor ??
//    }
//
//    @Override
//    public String toString() {
//        return "History{" +
//                "call_history_id=" + call_history_id +'\'' +
//                "person_id=" + person_id + '\'' +
//                "company_id=" + company_id + '\'' +
//                "call_started=" + call_started+  '\'' +
//                '}';
//    }
//
//}
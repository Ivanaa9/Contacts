import java.sql.Timestamp;
import java.time.Instant;

public class Timestamp1 {
    public static void main(String[] args) {

        //from() method Obtains an instance of Timestamp from an Instant object
        Timestamp instant= Timestamp.from(Instant.now());
        System.out.println("1. from() method will return "+instant);

        // valueOf() method returns a Timestamp value corresponding to the given string
        String str="2018-09-01 09:01:15";
        Timestamp timestamp= Timestamp.valueOf(str);
        System.out.println("2. value of Timestamp : "+timestamp);

        //getNanos() method gets the Timestamp object's nanos value
        Integer val=timestamp.getNanos();
        System.out.println("3. Fractional seconds component : "+val);
        Timestamp ts2 = Timestamp.valueOf("2018-09-01 09:01:16");

        //before() returns Boolean value true if this ts1 comes earlier than given ts2
        System.out.println("4. Boolean value returned : "+timestamp.before(ts2));
    }
}

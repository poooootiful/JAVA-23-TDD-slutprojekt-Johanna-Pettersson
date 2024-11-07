import java.util.HashMap;
import java.util.Map;

public class MockBank implements Bankinterface{

    public Map <String, User> users = new HashMap<>();

    public  User getUserById (String id) {
        return  users.get(id);
    }

    public boolean isCardLocked (String userid) {
        User user = users.get(userid);
        return user != null && user.isLocked();
    }
}
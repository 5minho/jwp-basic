package core.db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import next.model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static String update(User newUser) throws Exception {
        User oldUser = findUserById(newUser.getUserId());
        if (oldUser == null) {
            throw new Exception(newUser.getUserId() + " 님은 없는 유저 입니다.");
        }
        oldUser.setName(newUser.getName());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setEmail(newUser.getUserId());
        return newUser.getUserId();
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}

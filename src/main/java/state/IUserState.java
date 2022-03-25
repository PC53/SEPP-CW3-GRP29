package state;

import model.User;

import java.util.Map;

public interface IUserState {

    /**
     *
     * @param user
     */
    void addUser(User user);

    /**
     *
     * @return
     */
    Map<String,User> getAllUsers();

    /**
     *
     * @return
     */
    User getCurrentUser();

    /**
     *
     * @param user
     */
    void setCurrentUser(User user);
}

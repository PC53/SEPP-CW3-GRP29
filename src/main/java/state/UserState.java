package state;

import model.User;

import java.util.Map;

public class UserState extends Object implements IUserState {

    public UserState() {

    }

    public UserState(IUserState other) {

    }
    
    @Override
    public void addUser(User user) {

    }

    @Override
    public Map<String, User> getAllUsers() {
        return null;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public void setCurrentUser(User user) {

    }
}

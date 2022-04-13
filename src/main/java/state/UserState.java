package state;

import model.GovernmentRepresentative;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserState extends Object implements IUserState {

    private Map<String, User> users;
    User currentUser;

    public UserState() {
        this.users = new HashMap<String, User>();
        this.currentUser = null;
        registerGovernmentRepresentatives();
    }

    public UserState(IUserState other) {
        this.currentUser = ((UserState)other).currentUser;
        this.users = new HashMap<String, User>(((UserState)other).users);
    }

    /**
     * Add pre-registered government representative accounts to the user state.
     */
    private void registerGovernmentRepresentatives() {
        GovernmentRepresentative account1 = new GovernmentRepresentative(
                    "margaret.thatcher@gov.uk",
                    "The Good times  ",
                    "margaret.thatcher@gov.uk");
        users.put("margaret.thatcher@gov.uk", account1);
    }

    @Override
    public void addUser(User user) {
        users.put(user.getEmail(),user);
    }

    @Override
    public Map<String, User> getAllUsers() {
        return users;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }
}

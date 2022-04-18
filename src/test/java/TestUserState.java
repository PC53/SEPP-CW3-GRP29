import static org.junit.jupiter.api.Assertions.*;

import command.LoginCommand;
import command.RegisterConsumerCommand;
import command.RegisterEntertainmentProviderCommand;
import controller.Context;
import logging.Logger;
import model.*;
import org.junit.jupiter.api.*;
import state.UserState;

import java.util.List;


public class TestUserState {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    @Test
    @DisplayName("Adding One Valid User")
    void addingOneConsumer(){
        UserState userState = new UserState();

        Consumer consumer = new Consumer(
                "pavan",
                "pc53@gmail",
                "09876543210",
                "qwerty123",
                "jdk14@gmail"
        );

        userState.addUser(consumer);
        assertEquals(userState.getAllUsers().get("pc53@gmail"),consumer);
    }

    @Test
    @DisplayName("Adding Different Valid Users")
    void addingOneOfAllUserType(){
        UserState userState = new UserState();

        Consumer consumer = new Consumer(
                "pavan",
                "pc53@gmail",
                "09876543210",
                "qwerty123",
                "jdk14@gmail"
        );

        EntertainmentProvider entertainmentProvider = new EntertainmentProvider(
                "BCCI",
                "some glass building",
                "lalitmodi@$$$",
                "Lalit modi",
                "thirdpartyhandler@gmail",
                "F!ghT th3 R@Pture",
                List.of("sg"),
                List.of("dont ask us@cricket")
        );

        GovernmentRepresentative newGovRep = new GovernmentRepresentative(
                "boris.johnson@gov.uk",
                "b0r!$.johnson",
                "boris.johnson@gov.uk");

        userState.addUser(consumer);
        userState.addUser(entertainmentProvider);
        userState.addUser(newGovRep);

        assertEquals(userState.getAllUsers().get("pc53@gmail"),consumer);
        assertEquals(userState.getAllUsers().get("thirdpartyhandler@gmail"),entertainmentProvider);
        assertEquals(userState.getAllUsers().get("boris.johnson@gov.uk"),newGovRep);
    }

    @Test
    @DisplayName("Adding One Consumer more than once")
    void addingConsumerMultipleTimes(){
        UserState userState = new UserState();

        Consumer consumer = new Consumer(
                "pavan",
                "pc53@gmail",
                "09876543210",
                "qwerty123",
                "jdk14@gmail"
        );

        userState.addUser(consumer);
        userState.addUser(consumer);

        userState.getAllUsers().remove("pc53@gmail");
        assertNotEquals(userState.getAllUsers().get("pc53@gmail"),consumer);
    }

    @Test
    @DisplayName("Adding multiple users with same email")
    void adding2UsersWithSameEmail(){
        UserState userState = new UserState();

        Consumer consumer = new Consumer(
                "pavan",
                "pc53@gmail",
                "09876543210",
                "qwerty123",
                "jdk14@gmail"
        );

        EntertainmentProvider entertainmentProvider = new EntertainmentProvider(
                "BCCI",
                "some glass building",
                "lalitmodi@$$$",
                "Lalit modi",
                "pc53@gmail",
                "F!ghT th3 R@Pture",
                List.of("sg"),
                List.of("dont ask us@cricket")
        );

        userState.addUser(consumer);
        userState.addUser(entertainmentProvider);

        assertNotEquals(userState.getAllUsers().get("pc53@gmail"),consumer);
        assertEquals(userState.getAllUsers().get("pc53@gmail"),entertainmentProvider);
    }

    @Test
    @DisplayName("Checking if current user is updated after login")
    void getCurrentUserAfterLogin(){
        Context context = new Context();

        (new RegisterConsumerCommand(
                "pavan",
                "abc@gmail",
                "09876543210",
                "qwerty123",
                "jdk14@gmail")).execute(context);

        (new LoginCommand("abc@gmail","qwerty123")).execute(context);

        assertEquals(context.getUserState().getAllUsers().get("abc@gmail"),
                context.getUserState().getCurrentUser());

    }

    @Test
    @DisplayName("Checking if current user is updated after login")
    void userStateDeepCopy(){
        UserState userState = new UserState();

        Consumer consumer = new Consumer(
                "pavan",
                "pc53@gmail",
                "09876543210",
                "qwerty123",
                "jdk14@gmail"
        );

        EntertainmentProvider entertainmentProvider = new EntertainmentProvider(
                "BCCI",
                "some glass building",
                "lalitmodi@$$$",
                "Lalit modi",
                "thirdpartyhandler@gmail",
                "F!ghT th3 R@Pture",
                List.of("sg"),
                List.of("dont ask us@cricket")
        );

        GovernmentRepresentative newGovRep = new GovernmentRepresentative(
                "boris.johnson@gov.uk",
                "b0r!$.johnson",
                "boris.johnson@gov.uk");

        userState.addUser(consumer);
        userState.addUser(entertainmentProvider);
        userState.addUser(newGovRep);

        userState.setCurrentUser(entertainmentProvider);

        UserState userStateCopy = new UserState(userState);

        assertEquals(userState.getCurrentUser(),userStateCopy.getCurrentUser());
        assertEquals(userState.getAllUsers(),userStateCopy.getAllUsers());

    }
}

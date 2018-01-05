package com.grubjack.cinema.web.controller;

import com.grubjack.cinema.web.command.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Urban Aleksandr
 */
public class RequestHelper {
    private static Logger log = LoggerFactory.getLogger(RequestHelper.class);

    private static RequestHelper instance = null;
    private Map<String, Command> commands = new HashMap<>();


    private RequestHelper() {
        commands.put("login", new LoginCommand());
        commands.put("checkLogin", new CheckLoginCommand());
        commands.put("tickets", new ShowTicketsCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("registerUser", new RegistrationUserCommand());
        commands.put("addUser", new AddUserCommand());
        commands.put("cancelMovie", new CancelMovieCommand());
        commands.put("addMovie", new AddMovieCommand());
        commands.put("createMovie", new CreateMovieCommand());
        commands.put("hall", new ShowHallCommand());
        commands.put("users", new ShowUsersCommand());
        commands.put("deleteUser", new DeleteUserCommand());
        commands.put("buyTicket", new BuyTicketCommand());
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        Command command = commands.get(action);
        if (command == null) {
            command = new ShowScheduleCommand();
        }
        log.info("Running command {}", command);
        return command;
    }

    public static RequestHelper getInstance() {
        if (instance == null) {
            instance = new RequestHelper();
        }
        return instance;
    }
}

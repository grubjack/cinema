package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Role;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.service.ServiceFactory;
import com.grubjack.cinema.util.ConfigManager;
import com.grubjack.cinema.util.DigestMD5Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class RegistrationUserCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(RegistrationUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        log.info("Executing with session id {}", req.getSession().getId());
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        req.getSession().setAttribute("firstname", firstname);
        req.getSession().setAttribute("lastname", lastname);
        req.getSession().setAttribute("email", email);
        req.getSession().setAttribute("password", password);
        String[] roles = req.getParameterValues("selectedRoles");
        if (ServiceFactory.getInstance().getUserService().getByEmail(email) != null) {
            log.info("Register user with firstname {}, lastname {},email {},roles {}", firstname, lastname, email, roles);
            User user = new User(firstname, lastname, email, DigestMD5Helper.computeHash(password));
            if (roles != null) {
                for (String role : roles) {
                    user.addRole(Role.valueOf(role));
                }
            }
            ServiceFactory.getInstance().getUserService().create(user);
        }
        return req.getParameter("from") == null ? ConfigManager.getInstance().getProperty(ConfigManager.LOGIN_PAGE_PATH) : req.getParameter("from").substring(req.getContextPath().length());
    }
}

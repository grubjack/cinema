package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Role;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.service.ServiceFactory;
import com.grubjack.cinema.util.DigestMD5Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.util.ConfigManager.*;

/**
 * {@code RegistrationUserCommand} implementation of interface {@code Command}
 */
public class RegistrationUserCommand implements Command {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(RegistrationUserCommand.class);

    /**
     * Get user parameters and save them in session
     * User password converts to MD5 hash
     * <p>
     * Add new user with selected roles
     * If user is already exist redirect to registration form again
     * @param request
     * @param response
     * @return forward to login page or registration form if user already exist
     * @throws DaoException exception for dao operations
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        log.info("Executing with session id {}", request.getSession().getId());
        String firstname = request.getParameter(FIRSTNAME_PARAM);
        String lastname = request.getParameter(LASTNAME_PARAM);
        String email = request.getParameter(EMAIL_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        request.getSession().setAttribute(FIRSTNAME_PARAM, firstname);
        request.getSession().setAttribute(LASTNAME_PARAM, lastname);
        request.getSession().setAttribute(EMAIL_PARAM, email);
        request.getSession().setAttribute(PASSWORD_PARAM, password);
        String[] roles = request.getParameterValues(SELECTED_ROLES_PARAM);
        if (ServiceFactory.getInstance().getUserService().getByEmail(email) == null) {
            log.info("Register user with firstname {}, lastname {},email {},roles {}", firstname, lastname, email, roles);
            User user = new User(firstname, lastname, email, DigestMD5Helper.computeHash(password));
            if (roles != null) {
                for (String role : roles) {
                    user.addRole(Role.valueOf(role));
                }
            }
            ServiceFactory.getInstance().getUserService().create(user);
            request.setAttribute("userAdded", "");
        } else {
            log.warn("User with email {} already exist", email);
            request.setAttribute("duplicateEmail", "");
            return new AddUserCommand().execute(request, response);
        }
        return new LoginCommand().execute(request, response);
    }
}

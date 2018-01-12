package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.util.ConfigManager.LOGGED_USER_ATTR;
import static com.grubjack.cinema.util.ConfigManager.USER_ID_PARAM;

/**
 * {@code DeleteUserCommand} implementation of interface {@code Command}
 */
public class DeleteUserCommand implements Command {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(DeleteUserCommand.class);

    /**
     * Get user id from request and delete user by this id
     *
     * @param request
     * @param response
     * @return path to users page
     * @throws DaoException exception for dao operations
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        log.info("Executing with session id {}", request.getSession().getId());
        String userId = request.getParameter(USER_ID_PARAM);
        if (userId != null && !userId.isEmpty()) {
            log.info("Delete user with id {}", Integer.parseInt(userId));
            ServiceFactory.getInstance().getUserService().delete(Integer.parseInt(userId));
            User loggedUser = (User) request.getSession().getAttribute(LOGGED_USER_ATTR);
            if (loggedUser != null && loggedUser.getId() == Integer.parseInt(userId)) {
                return new LogoutCommand().execute(request, response);
            }
        }
        return new ShowUsersCommand().execute(request, response);
    }
}

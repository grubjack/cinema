package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.util.ConfigManager.USER_ID_PARAM;

public class DeleteUserCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(DeleteUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        log.info("Executing with session id {}", request.getSession().getId());
        String userId = request.getParameter(USER_ID_PARAM);
        if (userId != null && !userId.isEmpty()) {
            log.info("Delete user with id {}", Integer.parseInt(userId));
            ServiceFactory.getInstance().getUserService().delete(Integer.parseInt(userId));
        }
        return new ShowUsersCommand().execute(request,response);
    }
}

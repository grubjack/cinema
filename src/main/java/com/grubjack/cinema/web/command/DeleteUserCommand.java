package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(DeleteUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        log.info("Executing with session id {}", req.getSession().getId());
        String userId = req.getParameter("userId");
        if (userId != null && !userId.isEmpty()) {
            log.info("Delete user with id {}", Integer.parseInt(userId));
            ServiceFactory.getInstance().getUserService().delete(Integer.parseInt(userId));
        }
        return new ShowUsersCommand().execute(req,resp);
    }
}

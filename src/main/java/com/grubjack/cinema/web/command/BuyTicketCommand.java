package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.util.ConfigManager.LOGGED_USER_ATTR;
import static com.grubjack.cinema.util.ConfigManager.TICKET_ID_PARAM;

/**
 * Created by Urban Aleksandr
 */
public class BuyTicketCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(BuyTicketCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        log.info("Executing with session id {}", request.getSession().getId());
        String ticketId = request.getParameter(TICKET_ID_PARAM);
        User user = (User) request.getSession().getAttribute(LOGGED_USER_ATTR);
        if (user != null && ticketId != null && !ticketId.isEmpty()) {
            log.info("Buy ticket with id {} by user with email {}", ticketId, user.getEmail());
            ServiceFactory.getInstance().getTicketService().buyTicket(Integer.parseInt(ticketId), user.getId());
        }
        return new ShowHallCommand().execute(request, response);
    }
}

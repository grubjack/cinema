package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Urban Aleksandr
 */
public class BuyTicketCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(BuyTicketCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) throws ServletException, DaoException, IOException {
        log.info("Executing with session id {}", req.getSession().getId());
        String ticketId = req.getParameter("ticketId");
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user != null && ticketId != null && !ticketId.isEmpty()) {
            log.info("Buy ticket with id {} by user with email {}", ticketId, user.getEmail());
            ServiceFactory.getInstance().getTicketService().buyTicket(Integer.parseInt(ticketId), user.getId());
        }
        return new ShowHallCommand().execute(req, response);
    }
}

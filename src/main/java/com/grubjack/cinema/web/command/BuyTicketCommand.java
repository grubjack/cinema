package com.grubjack.cinema.web.command;

import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Role;
import com.grubjack.cinema.model.User;
import com.grubjack.cinema.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.grubjack.cinema.util.ConfigManager.LOGGED_USER_ATTR;
import static com.grubjack.cinema.util.ConfigManager.TICKET_ID_PARAM;

/**
 * {@code BuyTicketCommand} implementation of interface {@code Command}
 */
public class BuyTicketCommand implements Command {
    /**
     * Class logger
     */
    private static Logger log = LoggerFactory.getLogger(BuyTicketCommand.class);

    /**
     * Get ticket id and logged user from request
     * Add ticket to users tickets
     *
     * @param request
     * @param response
     * @return path to schedules page
     * @throws DaoException exception for dao operations
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        log.info("Executing with session id {}", request.getSession().getId());
        String ticketId = request.getParameter(TICKET_ID_PARAM);
        User loggedUser = (User) request.getSession().getAttribute(LOGGED_USER_ATTR);
        if (loggedUser != null && ticketId != null && !ticketId.isEmpty()) {
            log.info("Buy ticket with id {} by user with email {}", ticketId, loggedUser.getEmail());
            if (loggedUser.hasRole(Role.ROLE_USER)) {
                ServiceFactory.getInstance().getTicketService().buyTicket(Integer.parseInt(ticketId), loggedUser.getId());
            } else {
                log.warn("Access denied: user {} without permissions tried to buy ticket with id", loggedUser, ticketId);
            }
        }
        return new ShowHallCommand().execute(request, response);
    }
}

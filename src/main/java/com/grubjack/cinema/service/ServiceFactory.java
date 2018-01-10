package com.grubjack.cinema.service;

import com.grubjack.cinema.dao.DaoFactory;

/**
 * Created by Urban Aleksandr
 */
public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }


    public UserService getUserService() {
        return new UserServiceImpl(DaoFactory.getInstance().getUserDao());
    }

    public TicketService getTicketService() {
        return new TicketServiceImpl(DaoFactory.getInstance().getTicketDao());
    }

    public ShowService getShowService() {
        return new ShowServiceImpl(DaoFactory.getInstance().getShowDao(), DaoFactory.getInstance().getTicketDao());
    }
}

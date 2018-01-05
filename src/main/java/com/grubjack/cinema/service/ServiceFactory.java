package com.grubjack.cinema.service;

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
        return new UserServiceImpl();
    }

    public TicketService getTicketService() {
        return new TicketServiceImpl();
    }

    public ShowService getShowService() {
        return new ShowServiceImpl();
    }
}

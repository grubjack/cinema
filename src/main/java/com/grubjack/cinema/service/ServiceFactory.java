package com.grubjack.cinema.service;

import com.grubjack.cinema.dao.DaoFactory;

/**
 * {@code ServiceFactory} singleton
 * <p>
 * Prodives factory methods for creating instances of service implementations
 */
public class ServiceFactory {
    /**
     * Instance of singleton
     */
    private static ServiceFactory instance;

    private ServiceFactory() {
    }

    /**
     * Lazy singleton implementation
     *
     * @return instance of singleton
     */
    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    /**
     * Return implementation of {@code UserService} interface passing it related dao implementation
     *
     * @return interface implementation
     */
    public UserService getUserService() {
        return new UserServiceImpl(DaoFactory.getInstance().getUserDao());
    }

    /**
     * Return implementation of {@code TicketService} interface passing it related dao implementation
     *
     * @return interface implementation
     */
    public TicketService getTicketService() {
        return new TicketServiceImpl(DaoFactory.getInstance().getTicketDao());
    }

    /**
     * Return implementation of {@code ShowService} interface passing it related dao implementation
     *
     * @return interface implementation
     */
    public ShowService getShowService() {
        return new ShowServiceImpl(DaoFactory.getInstance().getShowDao(), DaoFactory.getInstance().getTicketDao());
    }
}

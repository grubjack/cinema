package com.grubjack.cinema.web.command;

import com.grubjack.cinema.util.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Urban Aleksandr
 */
public class NoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ConfigManager.getInstance().getProperty(ConfigManager.LOGIN_PAGE_PATH);
    }
}

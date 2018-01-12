package com.grubjack.cinema.dao;

import com.grubjack.cinema.DBHelper;
import com.grubjack.cinema.dao.UserDao;
import com.grubjack.cinema.dao.impl.UserDaoImpl;
import com.grubjack.cinema.exception.DaoException;
import com.grubjack.cinema.model.Role;
import com.grubjack.cinema.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserDaoImplTest {

    private UserDao userDao = new UserDaoImpl(DBHelper.getDataSource());
    private User testUser = new User("Test", "User", "test@i.ua", "Tt12345");

    @Before
    public void setUp() {
        DBHelper.setUpDatabase();
    }

    @Test
    public void testFindAll() throws DaoException {
        Assert.assertEquals(12, userDao.findAll().size());
    }

    @Test
    public void testUpdate() throws DaoException {
        userDao.create(testUser);
        User user = userDao.getByEmail(testUser.getEmail());
        user.setEmail("test2@i.ua");
        userDao.update(user);
        Assert.assertEquals(user.getEmail(), userDao.find(testUser.getId()).getEmail());
    }

    @Test
    public void testDelete() throws DaoException {
        userDao.delete(1);
        Assert.assertEquals(11, userDao.findAll().size());
    }

    @Test
    public void testCreate() throws DaoException {
        testUser.addRole(Role.ROLE_ADMIN);
        userDao.create(testUser);
        Assert.assertEquals(13, userDao.findAll().size());
    }

    @Test
    public void testGetByEmail() throws DaoException {
        userDao.create(testUser);
        Assert.assertEquals(testUser.getId(), userDao.getByEmail(testUser.getEmail()).getId());
    }

    @Test
    public void testFind() throws DaoException {
        userDao.create(testUser);
        Assert.assertEquals(testUser.getId(), userDao.find(testUser.getId()).getId());
    }

    @Test
    public void testFindNull() throws DaoException {
        Assert.assertNull(userDao.find(0));
    }

    @Test
    public void testCreateExistEmail() {
        try {
            userDao.create(testUser);
            userDao.create(testUser);
        } catch (DaoException e) {
            Assert.assertEquals("User with this email already exist", e.getMessage());
        }
    }
}
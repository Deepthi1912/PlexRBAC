package com.plexobject.rbac.dao.bdb;

import java.io.File;
import java.util.Collection;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.plexobject.rbac.domain.Application;
import com.plexobject.rbac.utils.CurrentUserRequest;

public class ApplicationDAOBDBTest {
    private static final Logger LOGGER = Logger
            .getLogger(ApplicationDAOBDBTest.class);

    private ApplicationDAOBDB dao;

    @Before
    public void setUp() throws Exception {
        CurrentUserRequest.startRequest("shahbhat", "127.0.0.1");

        FileUtils.deleteDirectory(new File("test_db_dir"));
        dao = new ApplicationDAOBDB("test_db_dir", "app");
    }

    @After
    public void tearDown() throws Exception {
        dao.close();

        FileUtils.deleteDirectory(new File("test_db_dir"));
        CurrentUserRequest.endRequest();
    }

    @Test
    public void testFindByUser() {
        final String username = "user " + System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Application app = new Application("name" + i, username);
            dao.save(app);
        }

        Collection<Application> all = dao.findByUser(username);
        for (Application app : all) {
            Assert.assertTrue(app.getID().startsWith("name"));
        }
        Assert.assertEquals(10, all.size());

        all = dao.findByUser(username + "x");
        Assert.assertEquals(0, all.size());

    }

    @Test
    public void testFindByName() {
        final String username = "user " + System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Application app = new Application("name" + i, username);
            dao.save(app);
        }
        for (int i = 0; i < 10; i++) {
            Application app = dao.findByID("name" + i);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("app " + app);
            }
            Assert.assertNotNull(app);
        }
    }

    @Test
    public void testFindAll() {
        final String username = "user " + System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Application app = new Application("name" + i, username);
            dao.save(app);
        }
        Collection<Application> all = dao.findAll(null, 10);
        for (Application app : all) {
            Assert.assertTrue(app.getID().startsWith("name"));
        }
        Assert.assertEquals(10, all.size());
    }

    @Test
    public void testFindByID() {
        final String username = "user " + System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Application app = new Application("name" + i, username);
            dao.save(app);
        }
        for (int i = 0; i < 10; i++) {
            Application app = dao.findByID("name" + i);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("app " + app);
            }
            Assert.assertNotNull(app);
        }
    }

    @Test
    public void testRemove() {
        final String username = "user " + System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Application app = new Application("name" + i, username);
            dao.save(app);
        }
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(dao.remove("name" + i));
        }
    }
}
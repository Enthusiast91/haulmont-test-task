package com.haulmont.backend.dao;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractEntityDAOTest {
    private AbstractEntityDAO entityDAO;
    AbstractEntityDAOTest(AbstractEntityDAO entityDAO) {
        this.entityDAO = entityDAO;
    }

    protected abstract Entity getUpdateEntity(Entity entity);

    protected abstract Entity getNewEntity();

    @Before
    public void setUp() {
        initDatabase();
    }

    @Test
    public void getAllTest() {
        System.out.println("Get all: ");
        List<Entity> entities = entityDAO.getAll();
        for (Entity entity : entities) {
            System.out.println(entity);
        }
    }

    @Test
    public void getEntityByIdTest() {
        System.out.println("Get entity by id 0, 1, 2:");
        for (int i = 0; i < 3; i++) {
            Entity entity = entityDAO.getEntityById(i);
            System.out.println(entity);
        }
    }

    @Test
    public void updateTest() {
        long id = 0;
        Entity entity = entityDAO.getEntityById(id);
        System.out.println("Before update:\n" + entity);

        entity = getUpdateEntity(entity);
        entityDAO.update(entity);
        entity = entityDAO.getEntityById(id);
        System.out.println("After update:\n" + entity);
    }

    @Test
    public void addTest() {
        Entity entity = getNewEntity();
        System.out.println("Added entity:\n" + entity);

        entityDAO.add(entity);
        List<Entity> entities = entityDAO.getAll();
        Entity lastEntity = entities.get(entities.size() - 1);
        System.out.println("Last entity from BD:\n" + lastEntity);
    }

    @Test
    public void deleteTest() {
        entityDAO.add(getNewEntity());
        List<Entity> entities = entityDAO.getAll();
        int entitiesSize = entities.size();
        System.out.println("Delete:\nSize before delete = " + entitiesSize);

        entityDAO.delete((long) (entitiesSize - 1));
        List<Entity> entitiesAfterDelete = entityDAO.getAll();
        int entitiesSizeAfterDelete = entitiesAfterDelete.size();
        System.out.println("Size after delete = " + entitiesSizeAfterDelete);
        assertEquals(entitiesSize - 1, entitiesSizeAfterDelete);
    }

    private static void initDatabase() {
        JDBCDao JDBC = HSQLDBDao.getInstance();

        try (Connection connection = DriverManager.getConnection(JDBC.getUrl(), JDBC.getUserName(), JDBC.getPass());
             Statement statement = connection.createStatement();
             BufferedReader sqlFileReader = new BufferedReader(new FileReader(JDBC.getPathToSQLDBInitFile()))) {
            try {
                Scanner scanner = new Scanner(sqlFileReader);
                String sqlLine;

                while (scanner.hasNextLine()) {
                    sqlLine = scanner.nextLine();
                    if (sqlLine.endsWith(";")) {
                        sqlLine = sqlLine.substring(0, sqlLine.length() - 1);
                    }
                    statement.execute(sqlLine);
                }
            } catch ( SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
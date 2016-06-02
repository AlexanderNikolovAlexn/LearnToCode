package com.samodeika.dao;

import com.samodeika.common.Dog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DogDaoImpl implements DogDao {

    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/alex", "root", "root");
    }

    @Override
    public List<Dog> getDogs() {
        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement("select * from dog");
            ResultSet rs = ps.executeQuery();
            List<Dog> dogs = new ArrayList<>();
            while(rs.next()) {
                Dog dog = new Dog(rs.getString("name"), rs.getInt("age"), rs.getDouble("weight"));
                dogs.add(dog);
            }

            return dogs;
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean saveDog(Dog dog) {
        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement("INSERT INTO dog(name, age, weight) values(?, ?, ?)");
            ps.setString(1, dog.getName());
            ps.setInt(2, dog.getAge());
            ps.setDouble(3, dog.getWeight());
            ps.execute();
            return true;
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean saveDogs(List<Dog> dogs) {
        try (Connection c = getConnection()) {
            c.setAutoCommit(false);
            for(Dog dog : dogs) {
                // TODO: could use bulkinsert
                PreparedStatement ps = c.prepareStatement("INSERT INTO dog(name, age, weight) values(?, ?, ?)");
                ps.setString(1, dog.getName());
                ps.setInt(2, dog.getAge());
                ps.setDouble(3, dog.getWeight());
                ps.execute();
            }
            c.commit();
            return true;
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

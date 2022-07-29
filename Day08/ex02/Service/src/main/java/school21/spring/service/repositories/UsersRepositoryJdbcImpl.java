package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component("jdbcRepository")
public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;

    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("driverManagerDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(Long id) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            String idQuery = "SELECT * FROM models.user WHERE id = ";
            ResultSet rs = st.executeQuery(idQuery + id);

            if (!rs.next()) {
                return null;
            }
            return new User(rs.getLong(1), rs.getString(2),
                    rs.getString("password"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            String alQuery = "SELECT * FROM models.user";
            ResultSet rs = st.executeQuery(alQuery);

            while (rs.next()) {
                users.add(new User(rs.getLong(1), rs.getString(2),
                        rs.getString("password")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return users.isEmpty() ? null : users;
    }

    @Override
    public void save(User entity) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            int result = st.executeUpdate("INSERT INTO models.user (id, email, password) VALUES ("
                    + entity.getId() + ", '"
                    + entity.getEmail() + "', '"
                    + entity.getPassword() + "');");

            if (result == 0) {
                System.err.println("User wasn't saved with id: " + entity.getId());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        String upQuery = "UPDATE models.user SET email = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(upQuery)) {
            st.setString(1, entity.getEmail());
            st.setLong(2, entity.getId());
            int result = st.executeUpdate();

            if (result == 0) {
                System.err.println("User wasn't updated with id: " + entity.getId());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String dlQuery = "DELETE FROM models.user WHERE id = ";
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(dlQuery + id)) {
            int result = st.executeUpdate();

            if (result == 0) {
                System.err.println("User not found with id: " + id);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String emQuery = "SELECT * FROM models.user WHERE email = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(emQuery)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }
            return Optional.of(new User(rs.getLong(1), rs.getString(2),
                    rs.getString("password")));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }
}

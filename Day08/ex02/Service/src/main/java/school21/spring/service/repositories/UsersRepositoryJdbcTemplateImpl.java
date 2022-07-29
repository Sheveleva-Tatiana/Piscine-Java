package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("jdbcTemplateRepository")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        String idQuery = "SELECT * FROM models.user WHERE id = :id";
        User user = (User) jdbcTemplate.query(idQuery,
                new MapSqlParameterSource().addValue("id", id),
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);

        return user;
    }

    @Override
    public List<User> findAll() {
        String alQuery = "SELECT * FROM models.user";
        List<User> users = jdbcTemplate.query(alQuery, new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    @Override
    public void save(User entity) {
        String inQuery = "INSERT INTO models.user (id, email, password) VALUES (:id, :email, :password)";
        int i = jdbcTemplate.update(inQuery, new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("email", entity.getEmail())
                .addValue("password", entity.getPassword()));

        if (i == 0) {
            System.err.println("User wasn't saved with id: " + entity.getId());
        }
    }

    @Override
    public void update(User entity) {
        String upQuery = "UPDATE models.user SET email = :email WHERE id = :id";
        int i = jdbcTemplate.update(upQuery, new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("email", entity.getEmail()));

        if (i == 0) {
            System.err.println("User wasn't updated with id: " + entity.getId());
        }
    }

    @Override
    public void delete(Long id) {
        String dlQuery = "DELETE FROM models.user WHERE id = :id";
        int i = jdbcTemplate.update(dlQuery, new MapSqlParameterSource()
                .addValue("id", id));

        if (i == 0) {
            System.err.println("User not found with id: " + id);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String emQuery = "SELECT * FROM models.user WHERE email = :email";
        User user = jdbcTemplate.query(emQuery,
                new MapSqlParameterSource().addValue("email", email),
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
        return Optional.ofNullable(user);
    }
}


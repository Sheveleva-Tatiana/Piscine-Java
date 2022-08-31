package sshera.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;
import sshera.models.Points;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@Component
public class PointsRepositoryImpl implements PointsRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PointsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS serverTanks;");
        jdbcTemplate.execute("DROP TABLE IF EXISTS serverTanks.points;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS serverTanks.points (\n" +
                "numberClient int,\n" +
                "shot int,\n" +
                "hit int );");
    }

    @Override
    public Points findById(Long id) {
        return null;
    }

    @Override
    public List<Points> findAll() {
        return null;
    }

    @Override
    public void saveClient(Integer numberClient) {
        String inQuery = "INSERT INTO serverTanks.points (numberClient, shot, hit) VALUES (?, ?, ?)";
        int i = jdbcTemplate.update(inQuery, numberClient, 0, 0);

        if (i == 0) {
            System.err.println("Client " + numberClient + " wasn't saved.");
        }
    }

    @Override
    public void updateHit(Integer numberClient) {
        String upQuery = "UPDATE serverTanks.points SET hit = hit + 1 WHERE numberclient = ?";
        int i = jdbcTemplate.update(upQuery, numberClient);

        if (i == 0) {
            System.err.println("Hit wasn't update for Client " + numberClient + ".");
        }
    }

    @Override
    public void updateShot(Integer numberClient) {
        String upQuery = "UPDATE serverTanks.points SET shot = shot + 1 WHERE numberclient = ?";
        int i = jdbcTemplate.update(upQuery, numberClient);

        if (i == 0) {
            System.err.println("Shot wasn't update for Client " + numberClient + ".");
        }
    }

    @Override
    public Points getInfo(int num) {
        String gQuery = "SELECT * FROM serverTanks.points WHERE numberclient = ?";

        return jdbcTemplate.query(gQuery,
                new Object[]{num},
                new BeanPropertyRowMapper<>(Points.class)).stream().findAny().orElse(null);
    }
}

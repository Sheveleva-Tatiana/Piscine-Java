package edu.school21.services.repositories;

import edu.school21.models.Product;
import edu.school21.repositories.ProductsRepository;
import edu.school21.repositories.ProductsRepositoryJdbcImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProductsRepositoryJdbcImplTest {
    private ProductsRepository repository;
    private DataSource dataSource;

    final List<Product> FIND_ALL = Arrays.asList(
            new Product(1l, "Pure", 420L),
            new Product(2l, "Love", 210L),
            new Product(3l, "Tea", 105l),
            new Product(4l, "Coffee", 1000l),
            new Product(5l, "Milk", 600l),
            new Product(6l, "Smile", 450L));

    final Product FIND_BY_ID = new Product(4l, "Coffee", 1000l);
    final Product UPDATED_PRODUCT = new Product(5l, "Lemon", 99l);
    final Product SAVE_PRODUCT = new Product(7l, "Fish", 690l);

    final List<Product> PRODUCTS_AFTER_DELETE = Arrays.asList(
            new Product(1l, "Pure", 420L),
            new Product(2l, "Love", 210L),
            new Product(4l, "Coffee", 1000l),
            new Product(5l, "Milk", 600l),
            new Product(6l, "Smile", 450L));

    @BeforeEach
    private void init() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        repository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void findAllTest() {
        assertEquals(FIND_ALL, repository.findAll());
    }

    @Test
    public void findByIdTest() throws SQLException {
        assertEquals(FIND_BY_ID, repository.findById(4l).get());
        assertEquals(Optional.empty(), repository.findById(10l));
        assertEquals(Optional.empty(), repository.findById(null));
    }

    @Test
    public void updateTest() throws SQLException {
        repository.update(new Product(5l, "Lemon", 99l));
        assertEquals(UPDATED_PRODUCT, repository.findById(5l).get());
    }

    @Test
    public void saveTest() throws SQLException {
        repository.save(new Product(7l, "Fish", 690l));
        assertEquals(SAVE_PRODUCT, repository.findById(7l).get());
    }

    @Test
    public void deleteTest() throws SQLException {
        repository.delete(3l);
        assertEquals(PRODUCTS_AFTER_DELETE, repository.findAll());
        assertFalse(repository.findById(3l).isPresent());
    }


}

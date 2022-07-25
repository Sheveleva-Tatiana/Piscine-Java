package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductsRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id) throws SQLException;
    void update(Product product);
    void save(Product product);
    void delete(Long id);
}

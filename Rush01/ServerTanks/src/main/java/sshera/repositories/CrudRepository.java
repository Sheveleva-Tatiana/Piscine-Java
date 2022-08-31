package sshera.repositories;

import java.util.List;

public interface CrudRepository<T> {
    T findById(Long id);
    List<T> findAll();
    void saveClient(Integer numberClient);
    void updateShot(Integer numberClient);
    void updateHit(Integer numberClient);

}

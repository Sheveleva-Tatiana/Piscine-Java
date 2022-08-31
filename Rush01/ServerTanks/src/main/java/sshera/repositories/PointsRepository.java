package sshera.repositories;

import sshera.models.Points;

public interface PointsRepository extends CrudRepository<Points> {
    Points getInfo(int num);
}

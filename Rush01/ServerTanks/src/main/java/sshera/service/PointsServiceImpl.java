package sshera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sshera.models.Points;
import sshera.repositories.PointsRepository;

@Component
public class PointsServiceImpl implements PointsService {
    private PointsRepository pointsRepository;

    @Autowired
    public PointsServiceImpl(PointsRepository pointsRepository) {
        this.pointsRepository = pointsRepository;
    }

    @Override
    public void createClient(int numberClient) {
        pointsRepository.saveClient(numberClient);
    }

    @Override
    public void addShot(int numberClient) {
        pointsRepository.updateShot(numberClient);
    }

    @Override
    public void addHit(int numberClient) {
        pointsRepository.updateHit(numberClient);
    }

    @Override
    public String getStatistics(int num) {
        Points info = pointsRepository.getInfo(num);
        Integer shot = info.getShot();
        Integer hit = info.getHit();
        Integer miss = shot - hit;
        if (num == 1) {
            num = 2;
        } else {
            num = 1;
        }
        Points info2 = pointsRepository.getInfo(num);
        Integer shot2 = info2.getShot();
        Integer hit2 = info2.getHit();
        Integer miss2 = shot2 - hit2;
        return "stat:" + shot + ":" + hit + ":" + miss + ":" + shot2 + ":" + hit2 + ":" + miss2;
    }
}

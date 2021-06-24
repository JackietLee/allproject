package net.pingfang.serviceImpl.vessel;

import net.pingfang.dao.vessel.VesselContainerRateDao;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.service.vessel.VesselContainerRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VesselContainerRateServiceImpl implements VesselContainerRateService {

    @Autowired
    private VesselContainerRateDao vesselContainerRateDao;

    @Override
    public List<WorkRecordVo> getAlreadyDeal(WorkRecordVo workRecordVo) {
            return vesselContainerRateDao.getAlreadyDeal(workRecordVo);
    }

    @Override
    public List<WorkRecordVo> getAll(WorkRecordVo workRecordVo) {
        return vesselContainerRateDao.getAll(workRecordVo);
    }
}

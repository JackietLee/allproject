package net.pingfang.service.vessel;

import net.pingfang.entity.work.WorkRecordVo;

import java.util.List;

public interface VesselContainerRateService {
    List<WorkRecordVo> getAlreadyDeal(WorkRecordVo workRecordVo);

    List<WorkRecordVo> getAll(WorkRecordVo workRecordVo);
}

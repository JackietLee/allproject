package net.pingfang.dao.vessel;

import net.pingfang.entity.work.WorkRecordVo;

import java.util.List;

public interface VesselContainerRateDao {

    List<WorkRecordVo> getAlreadyDeal(WorkRecordVo workRecordVo);

    List<WorkRecordVo> getAll(WorkRecordVo workRecordVo);
}

package net.pingfang.controller.vessel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.pingfang.entity.vessel.VesselContainerVo;
import net.pingfang.entity.work.WorkRecordVo;
import net.pingfang.handler.Result;
import net.pingfang.service.vessel.VesselContainerRateService;
import net.pingfang.service.vessel.VesselContainerService;
import net.pingfang.utils.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("vesselRate Api")
@RestController
@RequestMapping("/vesselRate")
public class VesselContainerRateController {

    @Autowired
    private VesselContainerRateService vesselContainerRateService;

    /**
     * 查询集装箱在船上的位置数据
     * @param
     * @return
     */
    @ApiOperation(value="查询装箱在船上的位置数据", notes="查询装箱在船上的位置数据")
    @ApiParam(name = "vesselContainerList", value = "船舶集装箱JSON数据")
    @GetMapping("/getVesselContainerList")
    @RequiresPermissions(value = {"getVesselContainerList"})
    public Result<Object> getVesselContainerList(@RequestParam WorkRecordVo workRecordVo) {
        List<WorkRecordVo> alreadyDeal = vesselContainerRateService.getAlreadyDeal(workRecordVo);
        List<WorkRecordVo> all = vesselContainerRateService.getAll(workRecordVo);
        Map map = new HashMap<>();
        map.put("all",all);
        map.put("alreadyDeal",alreadyDeal);
        return ResultUtil.success(map);
    }
}

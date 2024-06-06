package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import website.hehe.pojo.Operation;
import website.hehe.service.OperationService;
import website.hehe.utils.Operations;
import website.hehe.utils.annotations.AccessLimit;
import website.hehe.utils.annotations.OperateLog;
import website.hehe.utils.result.Result;

import java.util.List;

@RestController
@RequestMapping("/operation")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class OperationController {
    private OperationService operationService;

//    @OperateLog(operateModel = "Operation", operateType = Operations.Get, operateDesc = "Attempt to get all operations")
    @AccessLimit
    @GetMapping("/getOperation")
    public Result<List<Operation>> getOperation(
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime) {
        return operationService.getOperationsBetweenTime(startTime, endTime);
    }
}

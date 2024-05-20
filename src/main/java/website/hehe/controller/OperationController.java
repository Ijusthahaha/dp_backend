package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import website.hehe.pojo.Operation;
import website.hehe.service.OperationService;
import website.hehe.utils.result.Result;

import java.util.List;

@RestController
@RequestMapping("/operation")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class OperationController {
    private OperationService operationService;

    @GetMapping("/getOperation")
    public Result<List<Operation>> getOperation(
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime) {
        return operationService.getOperationsBetweenTime(startTime, endTime);
    }
}

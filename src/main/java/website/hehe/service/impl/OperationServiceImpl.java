package website.hehe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import website.hehe.pojo.Operation;
import website.hehe.service.OperationService;
import website.hehe.mapper.OperationMapper;
import org.springframework.stereotype.Service;
import website.hehe.utils.result.Result;

import java.util.List;

/**
* @author hehe
* @description 针对表【operation】的数据库操作Service实现
* @createDate 2024-05-12 14:54:39
*/
@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation>
    implements OperationService{

    @Autowired
    private OperationMapper operationMapper;


    @Override
    public Result<List<Operation>> getOperationsBetweenTime(String startTime, String endTime) {
        if (startTime == null) {
            // Set startTime to 24 hours ago in milliseconds
            startTime = (System.currentTimeMillis() - (24 * 60 * 60 * 1000)) + "";
        }
        if (endTime == null) {
            // Set endTime to current time in milliseconds
            endTime = System.currentTimeMillis() + "";
        }
        return Result.success(operationMapper.selectByTime(startTime, endTime));
    }
}





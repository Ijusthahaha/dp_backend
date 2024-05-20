package website.hehe.service;

import website.hehe.pojo.Operation;
import com.baomidou.mybatisplus.extension.service.IService;
import website.hehe.utils.result.Result;

import java.util.List;

/**
* @author hehe
* @description 针对表【operation】的数据库操作Service
* @createDate 2024-05-12 14:54:39
*/
public interface OperationService extends IService<Operation> {
    Result<List<Operation>> getOperationsBetweenTime(String startTime, String endTime);
}

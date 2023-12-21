package website.hehe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.hehe.mapper.AppealMapper;
import website.hehe.mapper.LogMapper;
import website.hehe.pojo.Appeal;
import website.hehe.pojo.Log;
import website.hehe.pojo.vo.AppealRequest;
import website.hehe.service.AppealService;
import website.hehe.utils.JwtUtils;
import website.hehe.utils.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hehe
 * @description 针对表【appeal】的数据库操作Service实现
 * @createDate 2023-12-18 18:56:07
 */
@Service
@Setter(onMethod_ = @Autowired)
public class AppealServiceImpl extends ServiceImpl<AppealMapper, Appeal> implements AppealService {

    private AppealMapper appealMapper;
    private LogMapper logMapper;
    private JwtUtils jwtUtils;

    @Override
    public Result<List<Map<String, Object>>> getAppeals(String token) {
        Integer userId = jwtUtils.getUserId(token);
        List<Map<String, Object>> appeals = appealMapper.selectAppealByUserId(userId);
        return Result.success(appeals);
    }

    @Override
    public Result<Object> createAppeals(String token, AppealRequest appealRequest) {

        Integer userId = jwtUtils.getUserId(token);

        Appeal appeal = new Appeal();
        appeal.setAppealStatus(1);
        appeal.setAppealReason(appealRequest.getAppealReason());
        appealMapper.insert(appeal);

        Integer appealId = appeal.getAppealId();

        Log log = new Log();
        log.setAppealId(appealId);
        Map<String, Object> queryParamsMap = new HashMap<>();
        queryParamsMap.put("log_id", appealRequest.getLogId());
        queryParamsMap.put("student_id", userId);

        logMapper.update(log, new UpdateWrapper<Log>().allEq(queryParamsMap));

        return Result.success(null);
    }
}





package website.hehe.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.hehe.mapper.AppealMapper;
import website.hehe.mapper.LogMapper;
import website.hehe.pojo.Appeal;
import website.hehe.pojo.Log;
import website.hehe.pojo.vo.AppealRequest;
import website.hehe.pojo.vo.LogRequest;
import website.hehe.pojo.vo.ModifyAppeal;
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

    @Transactional
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

        int update = logMapper.update(log, new UpdateWrapper<Log>().allEq(queryParamsMap));

        return Result.success(update);
    }

    @Override
    public Result<List<LogRequest>> getPendingAppeals(String token) {
        List<LogRequest> logList = logMapper.selectLogsByAppealIdExcludeNonPendingAppeals();
        return Result.success(logList);
    }

    @Override
    public Result<Object> rejectAppeals(String token, ModifyAppeal modifyAppeal) {
        UpdateWrapper<Appeal> appealUpdateWrapper = new UpdateWrapper<>();
        appealUpdateWrapper.eq("appeal_id", modifyAppeal.getAppealId());

        Appeal appeal = new Appeal();
        appeal.setAppealStatus(3);
        appeal.setAppealReason(modifyAppeal.getAppealReason());
        int update = appealMapper.update(appeal, appealUpdateWrapper);
        return Result.success(update);
    }

    @Override
    public Result<Object> fulfillAppeals(String token, ModifyAppeal modifyAppeal) {
        UpdateWrapper<Appeal> appealUpdateWrapper = new UpdateWrapper<>();
        appealUpdateWrapper.eq("appeal_id", modifyAppeal.getAppealId());

        Appeal appeal = new Appeal();
        appeal.setAppealStatus(2);
        int update = appealMapper.update(appeal, appealUpdateWrapper);
        return Result.success(update);
    }
}
package website.hehe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import website.hehe.pojo.Appeal;
import website.hehe.pojo.vo.AppealRequest;
import website.hehe.pojo.vo.LogRequest;
import website.hehe.pojo.vo.ModifyAppeal;
import website.hehe.utils.result.Result;

import java.util.List;
import java.util.Map;

/**
 * @author hehe
 * @description 针对表【appeal】的数据库操作Service
 * @createDate 2023-12-18 18:56:07
 */
public interface AppealService extends IService<Appeal> {
    Result<List<Map<String, Object>>> getAppeals(String token);

    Result<Object> createAppeals(String token, AppealRequest appealRequest);

    Result<List<LogRequest>> getPendingAppeals(String token);

    Result<Object> rejectAppeals(String token, ModifyAppeal modifyAppeal);

    Result<Object> fulfillAppeals(String token, ModifyAppeal modifyAppeal);
}

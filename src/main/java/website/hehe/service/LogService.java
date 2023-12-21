package website.hehe.service;

import website.hehe.pojo.Appeal;
import website.hehe.pojo.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import website.hehe.pojo.vo.CompareLog;
import website.hehe.utils.Result;

import java.util.List;
import java.util.Map;

/**
* @author hehe
* @description 针对表【log】的数据库操作Service
* @createDate 2023-12-14 12:44:01
*/
public interface LogService extends IService<Log> {

    Result<List<Log>> getLogs(String token);

    Result<List<Map<String, Object>>> getCompareLogs();

    Result<List<Log>> getRawLogs(String token);
}

package website.hehe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import website.hehe.pojo.Class;
import website.hehe.service.ClassService;
import website.hehe.mapper.ClassMapper;
import org.springframework.stereotype.Service;

/**
* @author hehe
* @description 针对表【class】的数据库操作Service实现
* @createDate 2023-12-18 18:55:38
*/
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class>
    implements ClassService{

}





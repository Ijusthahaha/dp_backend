package website.hehe.utils.annotations;

import website.hehe.utils.Operations;

import java.lang.annotation.*;

@Target(ElementType.METHOD) // 注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) // 注解在哪个阶段执行
@Documented
public @interface OperateLog {
    String operateModel() default ""; // 操作模块

    Operations operateType() default Operations.Null;  // 操作类型

    String operateDesc() default "";  // 操作说明
}
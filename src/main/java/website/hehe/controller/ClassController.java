package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.hehe.service.ClassService;
import website.hehe.utils.Result;

@RestController
@RequestMapping("/class")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class ClassController {

    private ClassService classService;

    @GetMapping("/getTotalClass")
    public Result<Integer> getTotalClass() {
        return Result.success(classService.list().size());
    }
}

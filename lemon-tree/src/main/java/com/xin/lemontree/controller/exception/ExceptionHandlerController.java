package com.xin.lemontree.controller.exception;

import com.xin.lemontree.vo.ResultVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author creator mafh 2018/2/7 11:04
 * @author updater mafh
 * @version 1.0.0
 * @description 将异常处理方法向上挪移到父类中，然后所有的 Controller 统一继承父类，现通过@ControllerAdvice增强可以不用继承，减少了与业务action耦合
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerController {

    /**
     * 统一异常处理
     *
     * @param e Exception
     */
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        throw new RuntimeException(e);
    }

    /**
     * 异常捕捉Action,主要用于捕捉Assets断言校验的异常验证输出！
     *
     * @param e IllegalArgumentException
     * @return 结果
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultVo handleIllegalArgumentException(IllegalArgumentException e) {
        return ResultVo.newResultVo(false, e.getMessage(), null);
    }

    /**
     * 捕捉action的校验异常
     *
     * @param e ValidateException
     * @return 结果
     */
    @ExceptionHandler(ValidateException.class)
    public ResultVo handleValidateException(ValidateException e) {
        return ResultVo.failureVo(e.getMessage());
    }
}

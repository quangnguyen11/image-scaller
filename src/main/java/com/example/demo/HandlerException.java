package com.example.demo;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(HandlerException.class);


    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public Object handleFileException(HttpServletRequest request, Throwable ex){
        Map<String, String> errData = new HashMap<>();
        errData.put("image", "file size exceed limit");
        Map<String, Object> errObj = new HashMap<>();
        errObj.put("status", 500);
        errObj.put("message", "internal server error");
        errObj.put("data", errData);
        return errObj;
    }

}

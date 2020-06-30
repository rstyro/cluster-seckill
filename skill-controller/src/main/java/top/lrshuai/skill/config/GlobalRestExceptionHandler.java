package top.lrshuai.skill.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.lrshuai.skill.commons.result.ApiException;
import top.lrshuai.skill.commons.result.ApiResultEnum;
import top.lrshuai.skill.commons.result.Result;

import java.io.IOException;

/**
 * 全局异常捕获
 * @author rstyro
 *
 */
@RestControllerAdvice
public class GlobalRestExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

	@ExceptionHandler(NullPointerException.class)
	public Result NullPointer(NullPointerException ex){
		logger.error(ex.getMessage(),ex);
		return Result.error(ApiResultEnum.ERROR_NULL);
	}

	@ExceptionHandler(ClassCastException.class)
	public Result ClassCastException(ClassCastException ex){
		logger.error(ex.getMessage(),ex);
		return Result.error(ApiResultEnum.ERROR_CLASS_CAST);
	}

	@ExceptionHandler(IOException.class)
	public Result IOException(IOException ex){
		logger.error(ex.getMessage(),ex);
		return Result.error(ApiResultEnum.ERROR_IO);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
		logger.error(ex.getMessage(),ex);
		return Result.error(ApiResultEnum.ERROR_MOTHODNOTSUPPORT);
	}


	@ExceptionHandler(ApiException.class)
	public Result ApiException(ApiException ex) {
		logger.error(ex.getMessage(),ex);
		return Result.error(ex.getStatus(),ex.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	public Result RuntimeException(RuntimeException ex){
		logger.error(ex.getMessage(),ex);
		return Result.error(ApiResultEnum.ERROR_RUNTION);
	}

	@ExceptionHandler(Exception.class)
	public Result exception(Exception ex){
		logger.error(ex.getMessage(),ex);
		return Result.error(ApiResultEnum.ERROR);
	}
	
}

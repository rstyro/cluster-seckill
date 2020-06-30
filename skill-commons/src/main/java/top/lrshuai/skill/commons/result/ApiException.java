package top.lrshuai.skill.commons.result;


import lombok.Data;

/**
 * 自定义的api异常
 * @author rstyro
 *
 */
@Data
public class ApiException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String status;
	private String message;
	private Object data;
	private Exception exception;
	private Object[] parameters;
	public ApiException() {
		super();
	}
	public ApiException(String status, String message, Object data, Object[] parameters, Exception exception) {
		this.status = status;
		this.parameters=parameters;
		for (int i = 0; this.parameters != null && i < this.parameters.length; i++) {
			message = message.replace("{" + i +"}", this.parameters[i].toString());
		}
		this.message = message;
		this.data = data;
		this.exception = exception;
	}
	public ApiException(ApiResultEnum apiResultEnum) {
		this(apiResultEnum.getStatus(),apiResultEnum.getMessage(),null,null,null);
	}
	public ApiException(ApiResultEnum apiResultEnum, Object... parameters) {
		this(apiResultEnum.getStatus(),apiResultEnum.getMessage(),null,parameters,null);
	}
	public ApiException(ApiResultEnum apiResultEnum, Object data, Exception exception) {
		this(apiResultEnum.getStatus(),apiResultEnum.getMessage(),data,null,exception);
	}

}

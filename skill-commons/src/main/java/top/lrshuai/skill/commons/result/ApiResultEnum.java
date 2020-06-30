package top.lrshuai.skill.commons.result;

public enum ApiResultEnum {
	SUCCESS("200","ok"),
	FAILED("400","请求失败"),
	ERROR("500","不知名错误"),
	ERROR_NULL("501","空指针异常"),
	ERROR_CLASS_CAST("502","类型转换异常"),
	ERROR_RUNTION("503","运行时异常"),
	ERROR_IO("504","上传文件异常"),
	ERROR_MOTHODNOTSUPPORT("505","请求方法错误"),
	SYSTEM_BUSY("506","服务器繁忙"),


	//参数
	PARAMETER_NULL("10001","缺少参数或值为空"),
	GOODS_SKILL_NOT_FOUND("10002","秒杀商品不存在或秒杀已结束"),
	GOODS_SKILL_NO_START("10003","秒杀未开始"),
	GOODS_SKILL_STOCK_SHORTAGE("10004","商品库存不足"),
	GOODS_NOT_REPEATABLE_PURCHASE("10005","限量商品不可重复购买"),



	//账户
	ACCOUNT_LOCK("20001","账号已锁定"),
	ACCOUNT_NOT_FOUND("20002","找不到账户信息"),
	ACCOUNT_PASSWARD_ERROR("20003","用户名密码错误"),
	ACCOUNT_EXIST("20004","账号已存在"),

	//权限
	AUTH_NOT_HAVE("30001","没有权限"),



	MANAGERCONTAINER_IS_NULL("40001","容器为空"),
	BUSINESS_RUN_ERROR("40002","业务执行失败"),


	REQUST_LIMIT("50001","请不要频繁点击"),
	REQUST_WEIXIN_ERROR("50002","第三方微信登录失败"),
	REQUST_COUNT_LIMIT("50003","超过每秒请求限制"),



	LIMIT_ERROR("60001","限流异常"),
	LIMIT_SILL("60002","请求人数过多，请稍后重试"),




	TOKEN_USER_INVALID("70000","Token过期或用户未登录"),

	;
	
	private String message;
	private String status;
	
	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}
	private ApiResultEnum(String status, String message) {
		this.message = message;
		this.status = status;
	}

	
}

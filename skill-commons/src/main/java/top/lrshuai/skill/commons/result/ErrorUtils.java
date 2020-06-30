package top.lrshuai.skill.commons.result;

public class ErrorUtils {
    public static void error(ApiResultEnum apiResultEnum, Object... params) {
        throw new ApiException(apiResultEnum, params);
    }

    public static void error(boolean logic, ApiResultEnum apiResultEnum, Object... params) {
        if(!logic) {
            return;
        }
        throw new ApiException(apiResultEnum, params);
    }
}

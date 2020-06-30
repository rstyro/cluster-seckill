package top.lrshuai.skill.manager;

import top.lrshuai.skill.commons.result.ApiResultEnum;
import top.lrshuai.skill.commons.result.ErrorUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ManagerContainer {
    // LinkedHashMap 保持顺序
    private static final Map<String, Manager> MANAGERS = new LinkedHashMap<>();

    public static <T extends Manager> T getManager(Class<T> cls) {
        Manager m = MANAGERS.get(cls.getSimpleName());
        if (m == null) {
            return null;
        }
        return (T) cls.cast(m);
    }

    public static void put(Manager manager){
        MANAGERS.put(manager.getClass().getSimpleName(),manager);
    }

    public static void start() throws Exception {
        if (MANAGERS.isEmpty()) {
            ErrorUtils.error(ApiResultEnum.MANAGERCONTAINER_IS_NULL);
        }
        for(Map.Entry<String, Manager> manage:MANAGERS.entrySet()){
            manage.getValue().start();
        }

    }

    public static void stop() {
        if (MANAGERS.isEmpty()) {
            ErrorUtils.error(ApiResultEnum.MANAGERCONTAINER_IS_NULL);
        }
        for (Map.Entry<String, Manager> element : MANAGERS.entrySet()) {
            element.getValue().stop();
        }
    }
}

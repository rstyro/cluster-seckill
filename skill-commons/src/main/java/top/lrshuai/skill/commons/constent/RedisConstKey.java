package top.lrshuai.skill.commons.constent;

/**
 * redis 缓存Key
 */
public class RedisConstKey {
    public final static String SKILL_GOODS="SKILL_GOODS";// 秒杀商品
    public final static String SKILL_GOODS_SUCCESS="SKILL_GOODS_SUCCESS";
    public final static String SKILL_GOODS_STOCK="SKILL_GOODS_STOCK"; // 秒杀商品库存


    public final static String SKILL_GOODS_QUEUE="SKILL_GOODS_QUEUE"; // 队列key

    public final static String SKILL_DELAYED_GOODS_QUEUE="SKILL_DELAYED_GOODS_QUEUE"; // 延迟队列key

    public static String append(Object... args){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if(i==args.length-1)sb.append(args[i]);
            else sb.append(args[i]).append(":");
        }
        return sb.toString();
    }
}

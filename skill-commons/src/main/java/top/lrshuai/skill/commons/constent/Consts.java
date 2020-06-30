package top.lrshuai.skill.commons.constent;

/**
 * 常量类
 */
public class Consts {
    public final static String IDEMPOTENT_KEY="IDEMPOTENT_KEY"; //幂等key




    public final static int YES=1;
    public final static int NO=0;

    public class GoodsType{
        public final static int NORMAL=1;
        public final static int SKILL=2;
        public final static int ACTIVITY=3;
    }
    public class OrderStatus{
        //-3:用户拒收
        public final static int REJECT=-3;
        //-2:未付款的订单
        public final static int UN_PAY=-2;
        //-1：用户取消
        public final static int CANCEL=-1;
        //0:待发货
        public final static int UN_DELIVER=0;
        //1:配送中
        public final static int DELIVER=1;
        //2:用户确认收货'
        public final static int RECEIPT=2;
    }
}

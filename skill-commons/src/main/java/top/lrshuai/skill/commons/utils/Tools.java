package top.lrshuai.skill.commons.utils;

import top.lrshuai.encryption.MDUtil;
import top.lrshuai.encryption.SHAUtil;

import java.util.Random;
import java.util.UUID;

/**
 * @author rstyro
 * @since 2017-08-18
 */
public class Tools {

    /**
     * 返回随机数
     *
     * @param n 个数
     * @return
     */
    public static String random(int n) {
        if (n < 1 || n > 10) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while (true) {
                int k = ran.nextInt(10);
                if ((bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char) (k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }

    public static String getUUID() {
        String str = UUID.randomUUID().toString();
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
                + str.substring(24);
    }

    /**
     * 指定范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static int getRandomNum(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s) {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }

    /**
     * 检测是否为数字
     *
     * @param s
     * @return
     */
    public static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
//		System.out.println(isNumber("22q3"));
        String shaPWD = SHAUtil.bcSHA1("zq123456");
        System.out.println(shaPWD);
        String md5PWD = MDUtil.jdkMD5(shaPWD);
        System.out.println(md5PWD);
    }
}

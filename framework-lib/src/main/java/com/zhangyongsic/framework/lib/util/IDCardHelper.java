package com.zhangyongsic.framework.lib.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class IDCardHelper {


    /**
     * 获取生日
     * yyyyMMdd
     * @param idCard
     * @throws Exception
     */
    public static String birth(String idCard) throws Exception {
        idCard = idCard15To18(idCard);
        return idCard.substring(6,14);
    }

    /**
     * 获取年龄
     * @param idCard
     * @throws Exception
     */
    public static Integer age(String idCard) throws Exception{
        idCard = idCard15To18(idCard);
        StringBuilder birth = new StringBuilder(idCard.substring(6,10)).append("-")
                .append(idCard.substring(10,12)).append("-").append(idCard.substring(12,14));
        LocalDate b = LocalDate.parse(birth);
        return b.until(LocalDate.now()).getYears();
    }

    /**
     * 获取性别
     * 1:男
     * 0:女
     * @param idCard
     * @throws Exception
     */
    public static Integer sex(String idCard) throws Exception{
        idCard = idCard15To18(idCard);
        String sex= idCard.substring(16,17);
        return Integer.valueOf(sex)%2;
    }

    /**
     * 获取性别
     * 男/女
     * @param idCard
     * @throws Exception
     */
    public static String sexText(String idCard) throws Exception{
        idCard = idCard15To18(idCard);
        String sex= idCard.substring(16,17);
        return Integer.valueOf(sex)%2==1?"男":"女";
    }

    /**
     * 15位身份证转18位身份证
     * @param idCard
     * @return
     * @throws Exception
     */
    private static String idCard15To18(String idCard) throws Exception{
        if (StringUtils.isEmpty(idCard) ||  (idCard.length()!=15 && idCard.length() != 18)){
            throw new Exception("不合法的身份证号码");
        }

        //todo 正则表达式验证身份证
        if (idCard.length()==15){
            StringBuilder sb = new StringBuilder();
            sb.append(idCard.substring(0, 6)).append("19").append(idCard.substring(6));
            sb.append(getVerifyCode(sb.toString()));
            return sb.toString();
        }
        return idCard;
    }


    /**
     * 获取校验码
     * @param idCard
     * @return
     * @throws Exception
     */
     public static char getVerifyCode(String idCard) throws Exception {
         if (idCard == null || idCard.length() < 17) {
             throw new Exception("不合法的身份证号码");
         }
         char[] Ai = idCard.toCharArray();
         int[] Wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
         char[] verifyCode = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
         int S = 0;
         int Y;
         for (int i = 0; i < Wi.length; i++) {
             S += (Ai[i] - '0') * Wi[i];
         }
         Y = S % 11;
         return verifyCode[Y];
     }

    public static void main(String[] args) throws Exception{
         String idcard = "511011199301058750";
        System.out.println(sex(idcard));
        System.out.println(birth(idcard));
        System.out.println(age(idcard));
        System.out.println(sexText(idcard));
    }
}

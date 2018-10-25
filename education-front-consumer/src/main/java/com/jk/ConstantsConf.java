package com.jk;

public class ConstantsConf {
	
	//购物商品缓存key	
	public static final String SHOP_CAR = "shopCarCode";
	
	//短信验证码缓存key
	public static final String Login_Code = "loginCode";
	
	//1分钟内不能重复获取标识符
	public static final String Login_Code_Flag = "loginCodeFlag";

	//短信验证码过期时间 单位分钟
	public static final Integer Login_Code_Time_OUT = 5;
	//短信平台地址ַ
	public static final String SMS_URL="https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
                                           
	//开发者信息
    public static final String ACCOUNTSID ="8767e18cc65241988c2d681b3497479c";
                                            
    //模板ID
    public static final String TEMPLATEID = "516087942";

    //查询Token
    public static final String AUTH_TOKEN="5ff8df0b80194ef5861cb546b4205b99";

}

package com.jk.config;

public class AlipayConfig {

    public static String app_id = "2016092200567294";//在后台获取（必须配置）

    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCScSyP6FulXKFV1TbZGAQZIzkamKm8xa2H9Gnl0AgnMxO3JEykaVEyJqskjpX+x9WgmI6nZAC0EwulR6r/xWv+lRVRyD+KdXPTVT9InVM8Mr8mliD8V4KZMUtLiFyh2LGl/rRAfRgYZYT/m2o+ueVv93FsShJq/Kb+5F9LdPf6EMLIdJoOqj84MZg85Yo5Hs1xQ7J/oBvxQdDVUFi8pvbbHyX80Me8lMVdwwrZfo+IGmn7ctKWAQ/JIA5/iY6WIecxxAFpbXA8vl7PKFrgnB3zhel76hN/FHCXXqdkm8+fNMzxbNlh0nwt8VV7rq5G/mSMpZdOE8/Qc3rmB5FlvgdjAgMBAAECggEAamU9iiS4iREeypzJZ7syJw1XTMKD9/nrVS63zddyRQCwH0yJPp6h9nzvtPS/d8e6px6P7oASnsKDoIbMb14BU4r/XVbPC4rAJ1f1TY24TrFT/fXva0ctRJ3y5aLiIlis4TyHu57nZbpEge1pqL8pOkseJh4++Wos7l3/+nO2QwDWBYppR8M/29rih9i7CKBMEQajdHSZzcprzghs5kSh5Y1QJdLwModOx1rn1fj/sFW8RCFUw+cJMP5nmOrRxGKUe30bkoLJxr4yIL+VwsyIzCPgDhP5nvuT33Y/3ZlMn9Uib94EEUIOuzqhLYpiG+D4KxR8kERQh6i6U8eDPh/9iQKBgQDTXFygS7yWkdN4nTAHKgJ/dS79JWn73Yz5e8qq9GAxJ6GoG+tbcSrWR3a6EOUg9+g4e5DeZ1L+vFFEY7Adv1OxYKyEs9V92aBRcWEQZMmgtMRDX4AMy/Euu+7lf8EUj6sSN9c8gyayHuwzeMGUD/5GtXO7k6ITmLDXk1dYd18W9QKBgQCxXtq/30fruHoZvPM+vQxEr+MoCX6Pl+VqD10Mn+r8CNyhNjsYxNEeA77jB30YKsyXWhPacakx7UmrrSKYNFO9mUwNbJLt1fmL+K++pTVm70GsCT3WtFBo1hYZDBHErxBsoGNOPNbKTfBUQ6Z8Cb8Tyq+DbpwpdPFoaRJIkVy99wKBgQCys1qVJLO0UmMLjUr4I6qs/PTXj3SGQJFatjFqTOevZ6SkL8bUL2Baf12Af5xaPYAXkVGw2IcxXSMQwYA8BnHKfMQgBrQwjXmgok3AwJZAy9VMaUgoiZ3HN5oKa/NhYP7wjknqnbBv1Pk7o5N3jfYc7nmFVJMqImUSPz8QSw0YqQKBgHHgEFl3pNk2C05S/WiePy4qLpEZ52ORhfzd+EOOuHMvzVbDVpXEZu36QafIuwu1Dr1tW6m3p4Uib1YFRlyp28myjh1gQ8Tmx9yRwWOI9gwsN+GX3VfnVGi6al2SXiYEr2rOXnNwpAYmm9G9zTl9mGvAiYa8kQ4cwpDiRJbKnNxpAoGAThnVz0tjTy19o7nkM0zvaDltRhaMkFhwEZSmHJYi9+F8tNoUgFaBCwLQjygxumNY0U9SgahALNyeNSsYQFvonUMGtckh0eb3z8uytWIdDV2kXvVJp5DnL9FaMhpqJp+z16xC3iSaDlFazpeK5xcdH7tixhGC/kK/OGaQ1Ik7wHs=";//教程查看获取方式（必须配置）

    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2kwHqrXYDt5hkM6NdyZn4aPpdR3ax7H7uw212NHBg2W9bPFyNHOaGvnvDNYlA2htmFS58bbCN0l9yNQwR4fOLsFxjeIL+7P6neAYOnI7dpe7KoLzGvQVPOMaDibd5YnHV0dIyVMDo1SZCi0a4Fr/Qf31QaByESmG3oR01pUek00gIkq6SVCMgU9E6YcJ2GGLb5VSmDZfy4HPuAL/hqD+USbNJSQs5n+T5CjM/REEcCXncvowfwGDnOWfF7SFl8mI996XWhiHh+ovkTLSRq2IN2L+FEE0gVDKMqs9vtpFVRBoG0oV2MBrHlVr10V2nOwOCri/jXf/y1+kyeJCelW3mwIDAQAB";//教程查看获取方式（必须配置）

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8092/test1.html";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8092/test2.html";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

}

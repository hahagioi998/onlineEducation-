package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import com.hnguigu.auth.UcenterAuthApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = UcenterAuthApplication.class)
@RunWith(SpringRunner.class)
public class TestRedis {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis(){
        //定义key
        String key = "user_token:bb049022-15a2-44a5-9364-3ef0528b0b58";
        //定义value
        Map<String,String> value = new HashMap<>();
        value.put("jwt","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiMSJdLCJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6IlhjV2ViQXBwIiwic2NvcGUiOlsiYXBwIl0sIm5hbWUiOm51bGwsInV0eXBlIjpudWxsLCJpZCI6bnVsbCwiZXhwIjoxNjAzMTIxMDk5LCJqdGkiOiJiYjA0OTAyMi0xNWEyLTQ0YTUtOTM2NC0zZWYwNTI4YjBiNTgiLCJjbGllbnRfaWQiOiJYY1dlYkFwcCJ9.m_KYSOTZuXxLSYNuZo4Ko6Z2TYa4hBSUvvqm39N_OAbtE4PzgWccquEIPxUX1z81pGevNxVSvl-QAT5yIN_SVRUlSe7fsqVL3ykCq-0WKBJpJ89wdP6zUljEs-jbBzkb_CshZbFghjAIrL2po_E9VoaX1OMmBxRANE7IKf3yzxEZnm9zyWB9NErss09ERe0Ogaa5FI9bVQAFqs4AJMVPsHpcMFYz1HXPDAqTaFb_9CQtkdrioGp4IoDw40-6In0KOriT2lTqxj0X3v3oA4tBl4ZESnDH3wQ_oGdQ89qWUbLi6FeOD2jEG1jqKoQ0C105UsrfN3QpagUzycABlollhQ");
        value.put("refresh_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiMSJdLCJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6IlhjV2ViQXBwIiwic2NvcGUiOlsiYXBwIl0sImF0aSI6ImJiMDQ5MDIyLTE1YTItNDRhNS05MzY0LTNlZjA1MjhiMGI1OCIsIm5hbWUiOm51bGwsInV0eXBlIjpudWxsLCJpZCI6bnVsbCwiZXhwIjoxNjAzMTIxMDk5LCJqdGkiOiI1Y2M1NmM0YS0zYjcwLTQ4NGEtYTg0Ni1iNmZhMzkxOTk5YjUiLCJjbGllbnRfaWQiOiJYY1dlYkFwcCJ9.TAm6zVkc5dMMN0SM63EwfAN7aC6VVetvKdBbVHucK6u_Jez0Hw0aNQXGAAOvmOEITHC0TABeO6FXJo7p_Bju0GecHY67Qven5qfj3u6Hm67PJkbmHmY8xqVRwx55Shkh_UWOcZ3BI7-i2vF3gwpQ2Xy0c_lsDI_LKgiNSbJCJYrFYqAmnWTLxu2u2odf45alqz6jIKx-KsjAB3GhsnuXQjk6X-P5ZhAYAzD4OKYLKyA4JxNLzNukDuoNJ_IY8MWCMQFjpLZGZlQ2nqa_JyRIEJxCncbNv_77HNYJNGmiJjnbxW7s2bEHocHLZ5XI0cKcsq3FDzmpjgVETfMQv3OCBw");
        String jsonString = JSON.toJSONString(value);
        //校验key是否存在
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        System.out.println(expire);
        //存储数据
        stringRedisTemplate.boundValueOps(key).set(jsonString,30, TimeUnit.SECONDS);
        //获取数据
        String s = stringRedisTemplate.opsForValue().get(key);
        System.out.println(s);
    }
}

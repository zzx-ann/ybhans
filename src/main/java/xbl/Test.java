package xbl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

    public String json_to_string()
    {
        /*json 对象*/
        String str = "{\"name\":\"c19\",\"type\":2,\"ts\":1517585798731,\"te\":1517585798731,\"header\":[6,\"xbllive\",\"820ceffd813f6e14\",\"863604039997312\",1,10933,\"1.1.1\",1517585636],\"ip\":\"123.161.99.160\",\"stime\":1517585799}" ;  // 一个未转化的字符串

        JSONObject json_body = JSONObject.fromObject(str); // 首先把json字符串转成 JSONArray  对象

        System.out.println("header" + "=" + json_body.get("header"));  // 打印header对象字符串


        JSONArray jsonObject = JSONArray.fromObject(json_body.get("header")); //得到header对象转数组
        //通过getString("")分别取出里面的信息
        String type = jsonObject.getString(0);
        String name = jsonObject.getString(1);
        System.out.println(type+" "+name);

 /*       JSONArray json_body = JSONArray.fromObject(str); // 首先把字符串转成 JSONArray  对象

        JSONObject job = json_body.getJSONObject(0);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
        System.out.println("header" + "=" + job.get("header"));  // 得到 每个对象中的属性值
        JSONArray jsonObject = JSONArray.fromObject(job.get("header"));
        //通过getString("")分别取出里面的信息
        String name = jsonObject.getString(1);
        String header = jsonObject.getString(2);
        System.out.println(name+" "+header);
*/
/*        if(json_body.size()>0) {
            for (int i = 0; i < json_body.size(); i++) {
                JSONObject job = json_body.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                System.out.println("header" + "=" + job.get("header"));  // 得到 每个对象中的属性值
                JSONArray jsonObject = JSONArray.fromObject(job.get("header"));
                //通过getString("")分别取出里面的信息
                String name = jsonObject.getString(1);
                String header = jsonObject.getString(2);
                System.out.println(name+" "+header);
            }
        }*/


      /*  //将json字符串转化为JSONObject
        JSONObject jsonObject = JSONObject.fromObject(str);
        //通过getString("")分别取出里面的信息
        String name = jsonObject.getString("name");
        String header = jsonObject.getString("header");
        //输出  张三 20
        System.out.println(name+" "+header);

        String jaStr = "[{user:{name:\"张三\",age:\"20\"}},{score:{yuwen:\"80\",shuxue:\"90\"}}]";
        //将jsonArray字符串转化为JSONArray
        JSONArray jsonArray = JSONArray.fromObject(jaStr);
        //取出数组第一个元素
        JSONObject jUser = jsonArray.getJSONObject(0).getJSONObject("user");
        //取出第一个元素的信息，并且转化为JSONObject
        String name2 = jUser.getString("name");
        String age2 = jUser.getString("age");
        //输出 张三 20
        System.out.println(name2+" "+age2);
        //取出数组第二个元素，并且转化为JSONObject
        JSONObject jScore = jsonArray.getJSONObject(1).getJSONObject("score");
        //取出第二个元素的信息
        String yuwen = jScore.getString("yuwen");
        String shuxue = jScore.getString("shuxue");
        //输出  80 90
        System.out.println(yuwen+"  "+shuxue);*/
        return "xxx";
    }


    public static void main(String[] args){

        Test example = new Test();
        System.out.println("area:" + example.json_to_string());
        // System.out.println("city :" + example.evaluate("1.0.32.11"));
    }

}

package xbl;
import com.google.common.collect.Lists;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.luaj.vm2.ast.Str;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.List;
import java.util.Map;

/**
 *   该拦截器将nginx日志格式化  按照指定分隔符分隔成一个一个的event header key,在sink中可以通过%{}方式来引用
 */
public class EventNginxLogFormat implements Interceptor {

  private final String splitChar;


  private EventNginxLogFormat(String splitChar) {
    this.splitChar = splitChar;
  }

  /*public void initialize()运行前的初始化，一般不需要实现（上面的几个都没实现这个方法）*/
  public void initialize() {
    // no-op
  }

  /**
   * Modifies events in-place.
   * public Event intercept(Event event)处理单个event
   */
  public Event intercept(Event event) {
    Map<String, String> headers = event.getHeaders();
    String event_body =   new String(event.getBody());
      System.out.println("event_body ########################"+event_body);
      for(Object o : headers.keySet()){
          System.out.println(o + ":" + headers.get(o));
      }

      /*取出 项目名称和日志类型id 放到event header 使用*/
      try{
          JSONObject json_body = JSONObject.fromObject(event_body); // 首先把json字符串转成 JSONArray  对象
          //.out.println("header" + "=" + json_body.get("header"));  // 打印header对象字符串
          JSONArray jsonObject = JSONArray.fromObject(json_body.get("header")); //得到header对象转数组
          String type = jsonObject.getString(0);
          String name = jsonObject.getString(1);
          String st = jsonObject.getString(7) + "000";
          headers.put("name", name);
          headers.put("type", type);
          headers.put("timestamp",st);
          System.out.println("add event header : name:"+name+",type:"+type+",timestamp"+st);

          return event;
      }catch (Exception e){
          headers.put("name", "other");
          headers.put("type", "other");
          headers.put("timestamp", System.currentTimeMillis()+"");
          return event;
      }

     /*
      // body 格式化
      try{
          String new_body = "hans:"+event_body;

          event.setBody(new_body.toString().getBytes());
      }catch (Exception e){
          System.out.println("erer #######");
          event=null;
      }*/

     /*if (headers.containsKey(Constants.KAFKA_MESSAGE_KEY)) {
          System.out.println("key in #######");
      String kafkaMessageKey = headers.get(Constants.KAFKA_MESSAGE_KEY); //从header中获取kafka message key
      String[] values = kafkaMessageKey.split(splitChar); //按照指定的分隔符将key拆分

      for (int i = 0; i < values.length; i++) {
        headers.put(Constants.SPLIT_KEY_PREFIX + i, values[i]);//拆分出来的每一个片段，按指定的前缀加上序号，写入event header
      }
      return event;

    } else {
      return null;
    }*/
  }

  /**
   * Delegates to {@link #intercept(Event)} in a loop.
   *
   * @param events
   * @return
   */
  public List<Event> intercept(List<Event> events) {
    List<Event> out = Lists.newArrayList();
    for (Event event : events) {
      Event outEvent = intercept(event);
      if (outEvent != null) {
        out.add(outEvent);
      }
    }
    return out;
  }

  public void close() {
    // no-op
  }

  /*public interface Builder extends Configurable 构建Interceptor对象，外部使用这个Builder来获取Interceptor对象*/
  public static class Builder implements Interceptor.Builder {

    private String splitChar = Constants.DEFAULT_SPLIT_CHAR;

    public Interceptor build() {
        System.out.println("splitChar*******************"+splitChar);
      return new EventNginxLogFormat(splitChar);
    }

    public void configure(Context context) {
        System.out.println("key*******************"+Constants.KAFKA_MESSAGE_KEY);
        System.out.println("s*******************"+Constants.SPLIT_KEY_PREFIX);
      splitChar = context.getString(Constants.SPLIT_CHAR, Constants.DEFAULT_SPLIT_CHAR);
    }
  }

  public static class Constants {
    public static String KAFKA_MESSAGE_KEY = "key";   //kafka source会将message key写进event header中一个叫"key"的header
    public static String SPLIT_CHAR = "split";
    public static String DEFAULT_SPLIT_CHAR = ":";
    public static String SPLIT_KEY_PREFIX = "s";
  }

}
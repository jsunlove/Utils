package com.jsun.Utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author guochunyuan
 * @create on  2019-09-20 9:22
 */
public class GsonUtils {

    //线程安全的
    private static final Gson GSON;
    private static final Gson GSON_WRITE_NULL; // 不过滤空值
    private static final JsonParser JSON_PARSER;
    static {
        GSON = new GsonBuilder().enableComplexMapKeySerialization() // 当Map的key为复杂对象时,需要开启该方法
//                .serializeNulls() // 当字段值为空或null时，依然对该字段进行转换
//                .excludeFieldsWithoutExposeAnnotation() // 打开Export注解，但打开了这个注解,副作用，要转换和不转换都要加注解
                .setDateFormat("yyyy-MM-dd HH:mm:ss") // 序列化日期格式  "yyyy-MM-dd"
//                .setPrettyPrinting() // 自动格式化换行
                .disableHtmlEscaping() // 防止特殊字符出现乱码
                .registerTypeAdapter(new TypeToken<Map<String, Object>>() {// 处理整型或者long 的时候 不转化为double
                }.getType(), (JsonDeserializer<Map<String, Object>>) (json, typeOfT, context) -> {
                    Map<String, Object> treeMap = new HashMap<String, Object>();
                    JsonObject jsonObject = json.getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                    for (Map.Entry<String, JsonElement> entry : entrySet) {
                        treeMap.put(entry.getKey(), entry.getValue());
                    }
                    return treeMap;
                }).create();
        GSON_WRITE_NULL = new GsonBuilder().enableComplexMapKeySerialization() // 当Map的key为复杂对象时,需要开启该方法
                .serializeNulls() // 当字段值为空或null时，依然对该字段进行转换
//                .excludeFieldsWithoutExposeAnnotation() // 打开Export注解，但打开了这个注解,副作用，要转换和不转换都要加注解
                .setDateFormat("yyyy-MM-dd HH:mm:ss") // 序列化日期格式  "yyyy-MM-dd"
//                .setPrettyPrinting() // 自动格式化换行
                .disableHtmlEscaping() // 防止特殊字符出现乱码
                .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
                }.getType(), (JsonDeserializer<Map<String, Object>>) (json, typeOfT, context) -> {
                    Map<String, Object> treeMap = new HashMap<String, Object>();
                    JsonObject jsonObject = json.getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                    for (Map.Entry<String, JsonElement> entry : entrySet) {
                        treeMap.put(entry.getKey(), entry.getValue());
                    }
                    return treeMap;
                }).create();

        JSON_PARSER = new JsonParser();
    }

    //获取gson解析器
    public static Gson getGson() {
        return GSON;
    }

    //获取JsonParser解析器
    public static JsonParser getJsonParser() {
        return JSON_PARSER;
    }

    //获取gson解析器 有空值 解析
    public static Gson getWriteNullGson() {
        return GSON_WRITE_NULL;
    }


    /**
     * 根据对象返回json  过滤空值字段
     */
    public static String toJsonString(Object object) {
        return GSON.toJson(object);
    }

    /**
     * 根据对象返回json  不过滤空值字段
     */
    public static String toJsonString(Object object, GsonSerializerFeature ser) {
        if (ser == GsonSerializerFeature.WriteMapNullValue) {
            return GSON_WRITE_NULL.toJson(object);
        }else if(ser == GsonSerializerFeature.WriteMapValue){
            return GSON.toJson(object);
        }else {
            return GSON.toJson(object);
        }
    }

    /**
     * 解析jsonstring 为JsonObject
     * @param jsonString
     * @return
     */
    public static JsonObject parseJsonObject(String jsonString){
        if (StringUtils.isEmpty(jsonString))
            return new JsonObject();
        JsonElement parse = JSON_PARSER.parse(jsonString);
        if (null != parse)
            return parse.getAsJsonObject();
        else
            return new JsonObject();
    }

    /**
     * 获取jsonObject的对应属性的字符串 不存在返回空 ""
     * @param jsonObject
     * @param memberName
     * @return
     */
    public static  String getString(JsonObject jsonObject,String memberName){
        if (jsonObjectIsNull(jsonObject))
            return StringUtils.EMPTY;
        if (!jsonObject.has(memberName))
            return StringUtils.EMPTY;
        JsonElement jsonElement = jsonObject.get(memberName);
        if (jsonElement.isJsonNull()){
            return StringUtils.EMPTY;
        }
        if (jsonElement instanceof JsonArray) {
            return jsonElement.getAsJsonArray().toString();
        }
        if (jsonElement instanceof JsonObject){
            return jsonElement.getAsJsonObject().toString();
        }
        return jsonElement.getAsString();
    }

    /**
     * 获取jsonObject的对应属性的int 不存在返回空 null
     * @param jsonObject
     * @param memberName
     * @return
     */
    public static  Integer getInteger(JsonObject jsonObject,String memberName){
        if (jsonObjectIsNull(jsonObject))
            return null;
        if (!jsonObject.has(memberName))
            return null;
        JsonElement jsonElement = jsonObject.get(memberName);
        return jsonElement.isJsonNull() ? null : jsonElement.getAsInt();
    }

    /**
     * jsonObject 判空
     * @param jsonObject
     * @return
     */

    public static boolean jsonObjectIsNull(JsonObject jsonObject){
        return null == jsonObject;
    }
    /**
     * 获取jsonObject的对应属性的int 不存在返回空 0
     * @param jsonObject
     * @param memberName
     * @return
     */
    public static  Integer getInt(JsonObject jsonObject,String memberName){
        if (jsonObjectIsNull(jsonObject))
            return 0;
        if (!jsonObject.has(memberName))
            return 0;
        JsonElement jsonElement = jsonObject.get(memberName);
        return jsonElement.isJsonNull() ? 0 : jsonElement.getAsInt();
    }

    /**
     * 获取jsonObject的对应属性的json数组 不存在返回空 不存在返回空JsonArray对象
     * @param jsonObject
     * @param memberName
     * @return
     */
    public static  JsonArray getJsonArray(JsonObject jsonObject,String memberName){
        if (jsonObjectIsNull(jsonObject))
            return new JsonArray();
        if (!jsonObject.has(memberName))
            return new JsonArray();
        JsonElement jsonElement = jsonObject.get(memberName);
        return jsonElement.isJsonNull() ? new JsonArray() : jsonElement.getAsJsonArray();
    }

    /**
     * 获取jsonObject的对应属性的jsonObject 不存在返回空JsonObject对象
     * @param jsonObject
     * @param memberName
     * @return
     */
    public static  JsonObject getJsonObject(JsonObject jsonObject,String memberName){
        if (jsonObjectIsNull(jsonObject))
            return null;
        if (!jsonObject.has(memberName))
            return new JsonObject();
        JsonElement jsonElement = jsonObject.get(memberName);
        return jsonElement.isJsonNull() ? new JsonObject() : jsonElement.getAsJsonObject();
    }


    /**
     * 将字符串转化对象
     *
     * @param json     源字符串
     * @param classOfT 目标对象类型
     * @param <T>
     * @return
     */
    public static <T> T strToJavaBean(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    /**
     * 将jsonElement转化对象
     *
     * @param json     源字符串
     * @param classOfT 目标对象类型
     * @param <T>
     * @return
     */
    public static <T> T jsonElementToJavaBean(JsonElement json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    /**
     * 将json转化为对应的实体对象
     * new TypeToken<List<T>>() {}.getType()
     * new TypeToken<Map<String, T>>() {}.getType()
     * new TypeToken<List<Map<String, T>>>() {}.getType()
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    /**
     * 将json转化为对应的实体对象
     * new TypeToken<List<T>>() {}.getType()
     * new TypeToken<Map<String, T>>() {}.getType()
     * new TypeToken<List<Map<String, T>>>() {}.getType()
     */
    public static <T> T fromJson(JsonElement jsonElement, Type typeOfT) {
        return GSON.fromJson(jsonElement, typeOfT);
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> strToList(String gsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        if (StringUtils.isEmpty(gsonString))
            return null;
        try {
            JsonArray arry = JSON_PARSER.parse(gsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(GSON.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * String 转成list中有map的
     * @param gsonString
     * @return
     */
    public static List<Map<String, Object>> strToListMap(String gsonString) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            JsonArray arry = JSON_PARSER.parse(gsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(jsonElementToMap(jsonElement));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * String转成map
     *
     * @param jsonString
     * @return
     */
    public static  Map<String, Object> strToMap(String jsonString) {
        return GSON.fromJson(jsonString, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    /**
     * JsonElement转成map
     *
     * @param jsonString
     * @return
     */
    public static  Map<String, Object> jsonElementToMap(JsonElement jsonString) {
        return GSON.fromJson(jsonString, new TypeToken<Map<String, Object>>() {
        }.getType());
    }


    public static void main(String[] args) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("a", 10);
//        map.put("b", "10");
//        map.put("c", 0);
//        map.put("d", "fsaf");
//        String json = GSON.toJson(map);
//        System.out.println(json);
//        String strs = "{\"paySuccessDesc\": \"恭喜您，已成功订购“影视包月会员”\",\"bgImgType\": 2,\"bgImg\": \"http://image.gitv.tv/images/20191010/fd/fd8eb9da6a72d2aaab12964a6bcd940a.jpg\",\"productImg\": \"\",\"selectProductImg\": \"\",\"changePhoneNummber\": \"http://image.gitv.tv/images/20191010/a8/_new_a8b313dcccba1a3230c3dc5d4de4e367.png\",\"selectChangePhoneNummber\": \"http://image.gitv.tv/images/20191010/14/_new_14855ccdfb3a38f5a5a8066989ce38df.png\",\"sendPassCode\": \"http://image.gitv.tv/images/20191010/e6/_new_e6c22560e22a15c8cfb68e111d9d0b11.png\",\"selectSendPassCode\": \"http://image.gitv.tv/images/20191010/5b/_new_5b007d0b6c1b2a47df90dbc19e6bff72.png\",\"toOrder\": \"http://image.gitv.tv/images/20191010/7c/_new_7c41105a9ad9c69e11fbbe18ee09e96a.png\",\"selectToOrder\": \"http://image.gitv.tv/images/20191010/7c/_new_7caf8e7983ec2b50deb54139c1522893.png\",\"sureChange\": \"http://image.gitv.tv/images/20191010/b7/_new_b70f6aa41bebcaff52641da659379ca2.png\",\"selectSureChange\": \"http://image.gitv.tv/images/20191010/0c/_new_0c1ccd96d25bfc5d78e0ad4a75c94420.png\",\"secondSure\": 0,\"payPageImg\": \"http://image.gitv.tv/images/20191010/21/2120259e71db0b8b90c9bff96f0fa745.jpg\"}";
//        Map<String, Object> maps = strToMap(strs);
//        System.out.println("strmap:"+GsonUtils.toJsonString(maps));
//        Set<Map.Entry<String, Object>> entries = maps.entrySet();
//        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, Object> next = iterator.next();
//            System.out.println(next.getKey() + "------->" + next.getValue());
//        }
//        List<Map<String,Object>> mapList = new ArrayList<>();
//        mapList.add(map);
//        mapList.add(map);
//        mapList.add(map);
//        String s = GsonUtils.toJsonString(mapList);
//        System.out.println("liststr:"+s);
//        List<Map<String, Object>> mapList1 = GsonUtils.strToListMap(s);
//        String s1 = GsonUtils.toJsonString(mapList1);
//        System.out.println("liststr:"+s1);

        String json = "{\"haha\":null,\"array\":[{\"username\":\"test\"},{\"username\":\"test2\"}]}";
        JsonObject asJsonObject = JSON_PARSER.parse(json).getAsJsonObject();
        JsonElement jsonElement1 = asJsonObject.get("hahas");
        System.out.println("haha:"+getString(asJsonObject,"array"));
        String asJsonArray= asJsonObject.get("array").getAsJsonArray().toString();
        System.out.println("array:"+asJsonArray);
        List<Map<String, Object>> mapList = strToListMap(asJsonArray);
        for (Map<String,Object> map :mapList){
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> next = iterator.next();
                System.out.println(next.getKey()+"-------"+ next.getValue());
            }
        }

//        for (JsonElement jsonElement:asJsonArray){
//            JsonObject jsonObject = GSON.fromJson(jsonElement, JsonObject.class);
//            System.out.println(jsonObject.get("username"));
//            System.out.println(jsonObject.get("usernames"));
//        }
    }
}

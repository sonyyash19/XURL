package com.crio.shorturl;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class XUrlImpl implements XUrl{

    private HashMap<String, String> map = new HashMap<>();
    private String shortUrl = "";
    private HashMap<String, Integer> hitLongUrlCount = new HashMap<>();

    @Override
    public String registerNewUrl(String longUrl) {
        if(map.containsKey(longUrl)){
            shortUrl = map.get(longUrl);
            return shortUrl;
        }

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    + "0123456789"
    + "abcdefghijklmnopqrstuvxyz";

     StringBuilder sb = new StringBuilder(9);

        for(int i = 0; i < 9; i++){
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        shortUrl = "http://short.url/" + sb;
        map.put(longUrl, shortUrl);
        return shortUrl;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        
        // if(map.containsKey(longUrl)){
        //     // throw new ConcurrentModificationException("Specified url already present.");
        //     return "Specified url already present";
        // }
        if(map.containsValue(shortUrl)){
            // throw new ConcurrentModificationException("Specified SHORTURL already present.");
            return null;
        }

        map.put(longUrl, shortUrl);
        
        return shortUrl;
    }

    // private static boolean isAlphaNumeric(String s) {
    //     return s != null && s.matches("^[a-zA-Z0-9]*$");
    // }

    @Override
    public String getUrl(String shortUrl) {
        if(!(map.containsValue(shortUrl))){
            return null;
        }

        String key = getKeys(map, shortUrl);

        if(hitLongUrlCount.containsKey(key)){
            int value = hitLongUrlCount.get(key);
            hitLongUrlCount.put(key,  value + 1);
        }else{
            hitLongUrlCount.put(key, 1);
        }

        return key;
    }

    private static String getKeys(Map<String, String> map, String value) {

        String key = "";
        if (map.containsValue(value)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (Objects.equals(entry.getValue(), value)) {
                    key = entry.getKey();
                }
            }
        }
        return key;
  
    }

    @Override
    public Integer getHitCount(String longUrl) {
        Integer hitCount = hitLongUrlCount.get(longUrl);
        if(hitCount == null){
            return 0;
        }
        return hitCount;
    }

    @Override
    public String delete(String longUrl) {
        return map.remove(longUrl);
    }

}
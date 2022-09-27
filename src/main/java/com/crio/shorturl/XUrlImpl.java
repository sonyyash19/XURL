package com.crio.shorturl;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class XUrlImpl implements XUrl{

    private HashMap<String, String> longToShort;
    private HashMap<String, String> shortToLong;
    private HashMap<String, Integer> hitLongUrlCount;
    SlugGenerator slugGenerator;
    private static final String URL = "http://short.url/";

    public XUrlImpl(){
        longToShort = new HashMap<>();
        shortToLong = new HashMap<>();
        hitLongUrlCount = new HashMap<>();
        slugGenerator = new SlugGenerator();

    }

    @Override
    public String registerNewUrl(String longUrl) {
        if(longToShort.containsKey(longUrl)){
            return URL + longToShort.get(longUrl);
        }

        String slug = slugGenerator.generateRandomString();

        longToShort.put(longUrl, slug);
        shortToLong.put(slug, longUrl);
        hitLongUrlCount.put(longUrl, 0);

        return URL + slug;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {

        String slug = shortUrl.replace(URL, "");
        
        if(longToShort.containsValue(slug)){
            return null;
        }

        longToShort.put(longUrl, slug);
        shortToLong.put(slug, longUrl);
        hitLongUrlCount.put(longUrl, 0);
        
        return URL + slug;
    }

    @Override
    public String getUrl(String shortUrl){

        String longUrl = shortToLong.get(shortUrl.replace(URL, ""));

        if(longUrl != null){
            hitLongUrlCount.compute(longUrl, (k ,v) -> v + 1);
        }

        return longUrl;
    }


    /* OLD IMPLEMENTATION FOR RETURNING LONG URL
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
  
    } */

    @Override
    public Integer getHitCount(String longUrl) {
        return hitLongUrlCount.getOrDefault(longUrl, 0);
    }

    @Override
    public String delete(String longUrl) {
        String slug = longToShort.remove(longUrl);
        shortToLong.remove(slug);
        return URL + slug;
    }

}
package org.example.shorturl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class UrlShortener {

    static final Integer SHORT_URL_LENGTH = 6;

    public String shorten(String original) throws RuntimeException{
        if(original.isEmpty()){
            throw new InvalidUrlException();
        }
        String shortened = getRandomShortenedString(original);
        while (url_map.containsKey(shortened)) {
            shortened = getRandomShortenedString(padRandomChars(original));
        }
        url_map.put(shortened, original);
        return shortened;
    }

    public String retrieve(String shortened) throws ShortUrlNotFoundException{
        if(!url_map.containsKey(shortened)) {
            throw new ShortUrlNotFoundException();
        }
        return url_map.get(shortened);
    }

    public boolean assignIfAvailable(String original, String shortened) {
        if(url_map.containsKey(shortened)) {
            return false;
        }
        url_map.put(shortened, original);
        return true;
    }

    private String padRandomChars(String original) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextLong() + original + random.nextLong();
    }

    private String getRandomShortenedString(String original) {
        return UUID.nameUUIDFromBytes(original.getBytes(StandardCharsets.UTF_8)).toString().substring(0, SHORT_URL_LENGTH);
    }

    private Map<String, String> url_map = new HashMap<>();

    public class InvalidUrlException extends RuntimeException{}
    public class ShortUrlNotFoundException extends Exception {}

}

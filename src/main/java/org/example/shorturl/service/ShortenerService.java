package org.example.shorturl.service;

import org.example.shorturl.dao.UrlDetailsDao;
import org.example.shorturl.model.UrlDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class ShortenerService {

    static final Integer SHORT_URL_LENGTH = 6;

    @Autowired
    UrlDetailsDao urlDetailsDao;

    public String shorten(String original) throws RuntimeException{
        if(original.isEmpty()){
            throw new InvalidUrlException();
        }
        String shortened = getRandomShortenedString(original);
        while (urlDetailsDao.get(shortened).isPresent()) {
            shortened = getRandomShortenedString(padRandomChars(original));
        }
        urlDetailsDao.save(new UrlDetails(shortened, original));
        return shortened;
    }

    public String retrieve(String shortened) throws ShortUrlNotFoundException{
        Optional<UrlDetails> details = urlDetailsDao.get(shortened);
        if(!details.isPresent()) {
            throw new ShortUrlNotFoundException();
        }
        return details.get().originalUrl;
    }

    public Boolean assignIfAvailable(String original, String shortened) {
        if(urlDetailsDao.get(shortened).isPresent()) {
            return false;
        }
        urlDetailsDao.save(new UrlDetails(shortened, original));
        return true;
    }

    private String padRandomChars(String original) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextLong() + original + random.nextLong();
    }

    private String getRandomShortenedString(String original) {
        return UUID.nameUUIDFromBytes(original.getBytes(StandardCharsets.UTF_8)).toString().substring(0, SHORT_URL_LENGTH);
    }

    public class InvalidUrlException extends RuntimeException{}
    public class ShortUrlNotFoundException extends Exception {}

}

package org.example.shorturl.dao;

import org.example.shorturl.model.UrlDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockUrlDetailsDaoImpl implements UrlDetailsDao{
    @Override
    public Optional<UrlDetails> get(String shortUrl) {
        return Optional.ofNullable(allUrls.get(shortUrl));
    }

    @Override
    public Boolean save(UrlDetails urlDetails) {
        allUrls.put(urlDetails.shortUrl, urlDetails);
        return true;
    }

    private Map<String, UrlDetails> allUrls = new HashMap<>();
}

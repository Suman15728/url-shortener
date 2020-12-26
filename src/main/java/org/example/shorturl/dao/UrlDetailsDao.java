package org.example.shorturl.dao;

import org.example.shorturl.model.UrlDetails;

import java.util.Optional;

public interface UrlDetailsDao{
    Optional<UrlDetails> get(String shortUrl);
    Boolean save(UrlDetails urlDetails);
}

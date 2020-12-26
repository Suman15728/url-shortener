package org.example.shorturl.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "url_details")
public class UrlDetails {
    @Id
    @Column(name = "short_url")
    public String shortUrl;

    @Column(name = "original")
    public String originalUrl;
}

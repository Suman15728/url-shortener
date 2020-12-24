package org.example.shorturl;

import org.junit.Assert;
import org.junit.Test;

public class UrlShortenerTest {

    static final String longUrl = "www.this-is-a-very-long-url.com/where-i-will-write-whatever-to-make-it-longer-and-unsharable";

    @Test(expected = UrlShortener.InvalidUrlException.class)
    public void emptyUrlThrowsError(){
        UrlShortener shortener = new UrlShortener();
        shortener.shorten("");
    }

    @Test
    public void nonEmptyUrlReturnsNonEmpty() {
        UrlShortener shortener = new UrlShortener();
        Assert.assertFalse(shortener.shorten("something.com").isEmpty());
    }

    @Test
    public void longUrlReturnsShorterUrl() {
        UrlShortener shortener = new UrlShortener();
        Assert.assertTrue(longUrl.length() > shortener.shorten(longUrl).length());
    }

    @Test
    public void retrieveReturnsOriginalUrl() throws Exception{
        UrlShortener shortener = new UrlShortener();
        String original = "www.original.com";
        Assert.assertSame(original, shortener.retrieve(shortener.shorten(original)));
    }

    @Test(expected = UrlShortener.ShortUrlNotFoundException.class)
    public void retrieveWrongUrlThrowsError() throws Exception {
        UrlShortener shortener = new UrlShortener();
        shortener.retrieve("something_random");
    }

    @Test
    public void userSpecifiedShortUrlRetrievesOriginal() throws Exception{
        UrlShortener shortener = new UrlShortener();
        String original = "original";
        String shortened = "shortened";
        shortener.assignIfAvailable(original, shortened);
        Assert.assertSame(original, shortener.retrieve(shortened));
    }

    @Test
    public void userSpecifiedUrlNotAssignedIfAlreadyTaken() {
        UrlShortener shortener = new UrlShortener();
        String shortened = "shortened";
        Assert.assertTrue(shortener.assignIfAvailable("original1", shortened));
        Assert.assertFalse(shortener.assignIfAvailable("original2", shortened));
    }

    @Test
    public void repeatedShorteningAssignsDifferentResults() {
        UrlShortener shortener = new UrlShortener();
        String original = "original";
        Assert.assertNotEquals(shortener.shorten(original), shortener.shorten(original));
    }

}
package org.example.shorturl.service;

import org.example.shorturl.dao.MockUrlDetailsDaoImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class ShortenerServiceTest {

    static final String longUrl = "www.this-is-a-very-long-url.com/where-i-will-write-whatever-to-make-it-longer-and-unsharable";
    private ShortenerService shortenerService;

    @Before
    public void setup(){
        shortenerService = new ShortenerService();
        shortenerService.urlDetailsDao = new MockUrlDetailsDaoImpl();
    }

    @Test(expected = ShortenerService.InvalidUrlException.class)
    public void emptyUrlThrowsError(){
        shortenerService.shorten("");
    }

    @Test
    public void nonEmptyUrlReturnsNonEmpty() {
        Assert.assertFalse(shortenerService.shorten("something.com").isEmpty());
    }

    @Test
    public void longUrlReturnsShorterUrl() {
        Assert.assertTrue(longUrl.length() > shortenerService.shorten(longUrl).length());
    }

    @Test
    public void retrieveReturnsOriginalUrl() throws Exception{
        String original = "www.original.com";
        Assert.assertSame(original, shortenerService.retrieve(shortenerService.shorten(original)));
    }

    @Test(expected = ShortenerService.ShortUrlNotFoundException.class)
    public void retrieveWrongUrlThrowsError() throws Exception {
        shortenerService.retrieve("something_random");
    }

    @Test
    public void userSpecifiedShortUrlRetrievesOriginal() throws Exception{
        String original = "original";
        String shortened = "shortened";
        shortenerService.assignIfAvailable(original, shortened);
        Assert.assertSame(original, shortenerService.retrieve(shortened));
    }

    @Test
    public void userSpecifiedUrlNotAssignedIfAlreadyTaken() {
        String shortened = "shortened";
        Assert.assertTrue(shortenerService.assignIfAvailable("original1", shortened));
        Assert.assertFalse(shortenerService.assignIfAvailable("original2", shortened));
    }

    @Test
    public void repeatedShorteningAssignsDifferentResults() {
        String original = "original";
        Assert.assertNotEquals(shortenerService.shorten(original), shortenerService.shorten(original));
    }

}
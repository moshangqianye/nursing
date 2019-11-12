package com.jqsoft.nursing;

import com.jqsoft.nursing.util.Util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testLongitudeLatitude(){
        String s1 = "117.31";
        String sResult = Util.getCanonicalLongitudeOrLatitude(s1);
        print(sResult);
        assertEquals("117.310000", sResult);
        s1="117.32123588";
        sResult = Util.getCanonicalLongitudeOrLatitude(s1);
        print(sResult);
        assertEquals("117.321236", sResult);
        s1="117.3899212";
        sResult = Util.getCanonicalLongitudeOrLatitude(s1);
        print(sResult);
        assertEquals("117.389921", sResult);
        s1="117";
        sResult = Util.getCanonicalLongitudeOrLatitude(s1);
        print(sResult);
        assertEquals("117.000000", sResult);
    }

    public void print(String s){
        System.out.println(s);
    }
}
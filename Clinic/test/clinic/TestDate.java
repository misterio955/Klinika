/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic;

import junit.framework.TestCase;
import org.junit.Assert;

/**
 *
 * @author Basian
 */
public class TestDate extends TestCase {

    Date date;

    public TestDate(String name) {
        super(name);
    }

    public void setUp() {
        date = new Date("2018-06-05 14:00:00", null);

    }

    public void testGetID() {
        String expected = date.getDay();
        String result = "2018-06-05";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }

    public void testAddHour() {
        int expected = date.getHoursBusy().size();
        date.addBusyHour("15:00:00");
        int result = date.getHoursBusy().size();
        Assert.assertTrue(expected < result);
        Assert.assertFalse(expected > result);

    }

}

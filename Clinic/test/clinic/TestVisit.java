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
public class TestVisit extends TestCase {

    Visit visit1 ;
    Visit visit2 ;

    public TestVisit(String name) {
      super(name);
   }

    public void setUp() {
    visit1 = new Visit("1", "2", "44324324321", "2018-06-05 11:15:00", "Oczekiwana");
    //visit2 = new Visit("2", "1", "2", "2018-06-06 13:15:00", "Oczekiwana");
    }

    
    public void testGetID() {
        String expected = visit1.getID();
        String result = "1";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
    public void testGetID_Doc() {
        String expected = visit1.getID_Doc();
        String result = "2";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
    public void testGetPesel_Pat() {
        String expected = visit1.getPesel_Pat();
        String result = "44324324321";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
    public void testGetDate() {
        String expected = visit1.getDate();
        String result = "2018-06-05 11:15:00";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
    public void testGetStatus() {
        String expected = visit1.getStatus();
        String result = "Oczekiwana";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
    public void testPrint(){
        String expected = visit1.print();
        String result = "ID=1, ID_Doc=2, ID_Pat=44324324321, date=2018-06-05 11:15:00, status=Oczekiwana";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }

}

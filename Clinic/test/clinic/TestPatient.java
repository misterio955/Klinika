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
public class TestPatient extends TestCase {

    Patient patient ;
    

    public TestPatient (String name) {
      super(name);
   }

    public void setUp() {
    patient = new Patient("1", "44324324321", "Adrian", "Bryla", "abryla@gmial.com");
    
    }

    
    public void testGetID() {
        String expected = patient.getID();
        String result = "1";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
     public void testGetDate() {
        String expected = patient.getPesel();
        String result = "44324324321";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
    public void testGetID_Doc() {
        String expected = patient.getImie();
        String result = "Adrian";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
    public void testGetPesel_Pat() {
        String expected = patient.getNazwisko();
        String result = "Bryla";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
   
    
    public void testGetStatus() {
        String expected = patient.getEmail();
        String result = "abryla@gmial.com";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
    public void testPrint(){
        String expected = patient.print();
        String result = "ID=1, pesel=44324324321, imie=Adrian, nazwisko=Bryla, telefon=abryla@gmial.com";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }

}

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
public class TestDoctor extends TestCase {

    Doctor doctor;

    public TestDoctor(String name) {
        super(name);
    }

    public void setUp() {
        doctor = new Doctor("1", "Adrian", "Bryla", "abryla", "Urolog",  "abryla@gmial.com","2");

    }

    public void testGetID() {
        String expected = doctor.getID();
        String result = "1";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }

    public void testGetPassword() {
        String expected = doctor.getPassword();
        String result = "abryla";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }

    public void testGetImie() {
        String expected = doctor.getImie();
        String result = "Adrian";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }

    public void testGetNazwisko() {
        String expected = doctor.getNazwisko();
        String result = "Bryla";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }

    public void testGetEmial() {
        String expected = doctor.getEmail();
        String result = "abryla@gmial.com";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }
    
    
    public void testGetSala() {
        String expected = doctor.getRoom();
        String result = "2";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }

    public void testPrint() {
        String expected = doctor.print();
        String result = "ID=1, imie=Adrian, nazwisko=Bryla, haslo=abryla, specjalizacja=Urolog, email=abryla@gmial.com, sala=2";
        Assert.assertEquals(expected, result);
        Assert.assertTrue(expected.equals(result));
        Assert.assertFalse(!expected.equals(result));
    }

}

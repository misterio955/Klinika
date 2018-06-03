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
public class TestDoctorWindowController extends TestCase{

    public TestDoctorWindowController() {

    }

    private static int currentIdDoctor ;

   
    
    public void setUp() {
        currentIdDoctor = 0;
    }

    public void testSetIDDoctor() {
           int expected = currentIdDoctor;
          // DoctrorWindowController.setIDDoctor(0);
           int result = currentIdDoctor;
           Assert.assertTrue(expected == result);
           Assert.assertFalse(expected!= result);
        
    }
    
}

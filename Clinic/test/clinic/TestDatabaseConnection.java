package clinic;

import java.sql.SQLException;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 *
 * @author Basian
 */
public class TestDatabaseConnection extends TestCase {

    DatabaseConnection dbConn;

    public TestDatabaseConnection(String name) {
        super(name);
    }

    public void setUp() throws SQLException, ClassNotFoundException {
        dbConn = new DatabaseConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/klinika", "root", "");

    }

    public void testRegisterPatient() {
        try {
            int expected = dbConn.getPatientsList().size();
            dbConn.registerPatient("90909090909", "Michal", "Kustra", "mkustra@o2.pl");
            int result = dbConn.getPatientsList().size();
            Assert.assertTrue(expected < result);
            Assert.assertFalse(expected > result);
        } catch (NullPointerException e) {
            System.out.println("Wyłapano NullPointerException");

        }

    }

    public void testRegisterDoctor() {
        try {
            int expected = dbConn.getDoctorsList().size();
            dbConn.registerDoctor("Michal", "Kustra", "mkustra", "Urolog", "mkustra@o2.pl", "7");
            int result = dbConn.getDoctorsList().size();
            Assert.assertTrue(expected < result);
            Assert.assertFalse(expected > result);
        } catch (NullPointerException e) {
            System.out.println("Wyłapano NullPointerException1");

        }

    }

    public void testCreateVisit() {
        try {
            int expected = dbConn.getVisitsList().size();
            dbConn.createVisit(dbConn.getDoctorByID("4"), dbConn.getPatientByPESEL("98762549875"), "2018-06-05 14:00:00");
            int result = dbConn.getVisitsList().size();
            Assert.assertTrue(expected < result);
            Assert.assertFalse(expected > result);
        } catch (NullPointerException e) {
            System.out.println("Wyłapano NullPointerException2");

        }

    }

}

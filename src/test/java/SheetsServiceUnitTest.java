import SheetREST.Application;
import SheetREST.model.SheetsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
public class SheetsServiceUnitTest {
	private SheetsService sheetsService;
	@Before
	public void setUp() {
		sheetsService = new SheetsService();
	}
	@Test
	public void findColumnIntegerTest() throws IOException, GeneralSecurityException {
		assertEquals(4, sheetsService.findColumnInteger("03-05-2019"));
	}
	@Test
	public void findColumnStringTest() throws IOException, GeneralSecurityException {
		assertEquals("D", sheetsService.findColumnString("03-05-2019"));
		assertEquals("E", sheetsService.findColumnString("04-05-2019"));
		assertEquals("B", sheetsService.findColumnString("01-05-2019"));
		assertEquals("Z", sheetsService.findColumnString("25-05-2019"));
		assertEquals("AA", sheetsService.findColumnString("26-05-2019"));
		assertEquals("BH", sheetsService.findColumnString("28-06-2019"));
		assertEquals("IH", sheetsService.findColumnString("27-12-2019"));
	}
}

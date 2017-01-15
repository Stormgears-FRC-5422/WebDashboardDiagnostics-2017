import org.stormgears.WebDashboard.Diagnostics.Diagnostics;
import org.stormgears.WebDashboard.WebDashboard;

import java.net.URISyntaxException;

/**
 * Created by andrew on 1/14/17.
 */
public class DiagTest {
	public static void main(String args[]) throws URISyntaxException {
		WebDashboard.init("localhost:5802");
		Diagnostics.init();
		System.out.println("MEOW");
//		WebDashboard.emit("log");
	}
}

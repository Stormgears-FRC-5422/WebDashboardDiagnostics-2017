import org.stormgears.WebDashboard.Diagnostics.Diagnostics;
import org.stormgears.WebDashboard.WebDashboard;

//import helpers.*;
import helpers.Main;
import java.io.InvalidClassException;
import java.net.URISyntaxException;

/**
 * Created by andrew on 1/14/17.
 */
public class DiagTest {
	public static void main(String args[]) throws Exception {
		WebDashboard.init("localhost:5802");
		Diagnostics.init(true);
		System.out.println("MEOW");

//		throw new IllegalArgumentException("MEOW");
//		WebDashboard.emit("log");
//
//		for (int i = 0; i < 1000; i++) {
//			Diagnostics.log("" + Math.random());
//		}

//		Main.main(new String[]{""});
		(new Thread(new Runnable() {
			@Override
			public void run() {
				throw new IllegalStateException("" + Math.random());
			}
		})).start();
	}
}

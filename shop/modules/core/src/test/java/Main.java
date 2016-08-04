import java.io.IOException;

import org.junit.Test;

import com.tuisitang.common.utils.Encodes;
import com.tuisitang.modules.shop.utils.Global;

public class Main {

	public static void main(String[] args) throws IOException {
		String aKey = "guDkRAwnLtJCB7Rm477E7Yg0Tc2GKkiNfb7PFBEmdtnMOQW1Vy0hw0t0FQrVe5lDe59I1qO7MgmobS8TqY6Un1PlLGnQwLGjdXT6cPYtqzD7ZXRAAcoTaZ7wiW26JoL1";
		String s = Encodes.rc4(new String(Encodes.decodeBase64("V8Kyw4oLw7JLKcO0wrkOw5U="),"utf-8"), aKey);
		System.out.println(s);
		
//		s = Encodes.rc4(new String(Encodes.decodeBase64(Global.getConfig("AlipayKey"))),
//				Global.getConfig("AppKey"));
//		System.out.println(s);
		
		System.out.println(Encodes.encodeBase64("111:18020260877".getBytes("utf-8")));
	}
	
	@Test
	public void test() throws IOException {
		String aKey = "guDkRAwnLtJCB7Rm477E7Yg0Tc2GKkiNfb7PFBEmdtnMOQW1Vy0hw0t0FQrVe5lDe59I1qO7MgmobS8TqY6Un1PlLGnQwLGjdXT6cPYtqzD7ZXRAAcoTaZ7wiW26JoL1";
		String s = Encodes.rc4(new String(Encodes.decodeBase64("V8Kyw4oLw7JLKcO0wrkOw5U="),"utf-8"), aKey);
		System.out.println(s);
		
		s = Encodes.rc4(new String(Encodes.decodeBase64(Global.getConfig("AlipayKey"))),
				Global.getConfig("AppKey"));
		System.out.println(s);
	}

}

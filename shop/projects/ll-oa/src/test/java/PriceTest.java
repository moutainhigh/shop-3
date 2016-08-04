import java.io.File;
import java.io.IOException;
import java.util.List;

import com.tuisitang.common.utils.FileUtils;


public class PriceTest {

	public static void main(String[] args) {
		try {
			List<String> lines = FileUtils.readLines(new File("/Users/xubin/Documents/work2015/jeesite/src/test/java/price.txt"));
			for (String line : lines) {
//				System.out.println(line + "," + line.split(" ").length + "," + line.split("\t").length);
				if(line.split(" ").length == 2) {
					double p1 = Double.parseDouble(line.split(" ")[0]);
					double p2 = Double.parseDouble(line.split(" ")[1]);
					System.out.println(line + ",上调率：" + (p1-p2)*100/p2);
				} else if(line.split("\t").length == 2) {
					double p1 = Double.parseDouble(line.split("\t")[0]);
					double p2 = Double.parseDouble(line.split("\t")[1]);
					System.out.println(line + ",上调率：" + (p1-p2)*100/p2);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

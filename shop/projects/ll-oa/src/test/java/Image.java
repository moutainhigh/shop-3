import java.net.URLDecoder;

import org.apache.commons.lang.StringEscapeUtils;


public class Image {

	public static void main(String[] args) throws Exception {
		String content = "&lt;img src=&quot;http://img.baoxiliao.com/attached/details/566/龙珠灯规格.png&quot; alt=&quot;&quot; width=&quot;800&quot; height=&quot;423&quot; title=&quot;&quot; align=&quot;left&quot; /&gt;";
		System.out.println(StringEscapeUtils.unescapeHtml(content));
		
	}

}

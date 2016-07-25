/**
 * 
 */
package rancher.stack.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @ClassName: UserLogin.java
 * @Description: TODO
 * @author: Rodd(Wang,Jian)
 * @email jian.wang2@hpe.com
 * @date Jun 20, 2016 4:22:44 PM
 */
public class UserLogin extends HttpServlet {

	/**
	 * 
	 */
	public UserLogin() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 登陆 Url
		URL url;
		String loginUrl = "http://c9t17878.itcs.hpecorp.net:9999/login?timedOut=true";
		// 需登陆后访问的 Url
		String dataUrl = "http://c9t17878.itcs.hpecorp.net:9999/apps/welcome";

		HttpClient httpClient = new HttpClient();

		// 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
		PostMethod postMethod = new PostMethod(loginUrl);

		// 设置登陆时要求的信息，用户名和密码
		NameValuePair[] data = { new NameValuePair("code", "rodd:123") };

		postMethod.setRequestBody(data);
		try {
			// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
			// httpClient.getParams().setCookiePolicy(CookiePolicy.COMPATIBILITY);
			httpClient.executeMethod(postMethod);

			System.out.println(postMethod.getResponseBodyAsString());
			postMethod.releaseConnection();
			CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
			Cookie[] cookies = cookiespec.match("c9t17878.itcs.hpecorp.net", 9999, "/", false,
					httpClient.getState().getCookies());
			// 获得登陆后的 Cookie
			StringBuffer tmpcookies = new StringBuffer();
			for (Cookie c : cookies) {
				System.out.println(c.toString());
				tmpcookies.append(c.toString() + ";");
			}
			// 进行登陆后的操作1581,1602,1603,1610,1609,1608,1607,1606,1605,1620,1619,1617,1616,1622,1626,1642,1648,1647,1657
			GetMethod getMethod = new GetMethod(dataUrl);
			// 每次访问需授权的网址时需带上前面的 cookie 作为通行证
			getMethod.setRequestHeader("cookie", tmpcookies.toString());
			// 你还可以通过 PostMethod/GetMethod 设置更多的请求后数据
			// 例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
			postMethod.setRequestHeader("Referer", "http://www.cc");
			postMethod.setRequestHeader("User-Agent", "www Spot");
			httpClient.executeMethod(getMethod);
			// 打印出返回数据，检验一下是否成功
			String text = getMethod.getResponseBodyAsString();
			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

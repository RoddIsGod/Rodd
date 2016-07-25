/**
 * 
 */
package com.transferData.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

/**
 * @ClassName: TransferDataDemo.java
 * @Description: TODO
 * @author: Rodd(Wang,Jian)
 * @email jian.wang2@hpe.com
 * @date Jun 14, 2016 11:36:59 AM
 */
public class TransferDataDemo extends HttpServlet {

	/**
	 * 
	 */
	public TransferDataDemo() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		System.out.println("called");
		try {
			String encoding = "UTF-8";
			File file = new File("C:\\D\\HP\\workspace\\transferData\\document\\dataDemo.txt");
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				// LineIterator list = IOUtils.lineIterator(read);
				// LineIterator it = FileUtils.lineIterator(file, "UTF-8");
				// while(list.hasNext()){
				// System.out.println(list.next());
				// out.println(list.next());
				// }
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println(lineTxt);
					out.println(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

		out.flush();
	}
}

package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("进入上传方法");
		//1.创建磁盘文件项工厂
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//2.创建文件上传的核心类
		ServletFileUpload upload=new ServletFileUpload(factory);	
		//3.解析请求体request--文件项集合
		List<FileItem> parseRequest=null;
		try {
			parseRequest=upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//4.遍历文件项集合
		for(FileItem item:parseRequest) {
			//5.判断文件项
			boolean formField=item.isFormField();//是否是一个普通表单项
			if(formField) {
				//普通表单项
				//获得字段名和字段值
				String name=item.getFieldName();
				String value=item.getString();
				System.out.println(name+":"+value);
			}else {
				//文件项 
				 String filename=item.getFieldName();
				 //获得上传文件的内容
				 InputStream in=item.getInputStream();
				 //将in的内容拷贝到服务器
				 String path=this.getServletContext().getRealPath("upload");
				 OutputStream out=new FileOutputStream(path+"/"+filename);
				 int len;
				 byte [] buf=new byte[1024];
				 while((len=in.read(buf))!=-1) {
					 out.write(buf, 0, len);
				 }
				 out.close();
				 in.close();
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import domain.Category;
import domain.Product;
import impl.AdminServiceImpl;
import service.AdminService;
import util.CommonUtils;

/**
 * Servlet implementation class AdminsaveProductServlet
 */
public class AdminsaveProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//收集表单数据，封装成product实体2.将上传的图片存储到服务器
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		List<FileItem> FileItems=null;
		upload.setHeaderEncoding("utf-8");
		
		Product product=new Product();
		//相当于收集数据的容器
		Map<String,Object> map=new HashMap<String,Object> ();
		try {
			FileItems = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(FileItem item:FileItems) {
			boolean formField=item.isFormField();
			if(formField) {
				//普通表单项 获得表单的数据封装到实体
				String fieldName=item.getFieldName();
				String value=item.getString("utf-8");
				map.put(fieldName, value);
				
			}else {
				//文件上传项 
				String name=item.getName();
				String path=this.getServletContext().getRealPath("upload");
				InputStream in=item.getInputStream();
				OutputStream out=new FileOutputStream(path+"/"+name);
				IOUtils.copy(in, out);
				in.close();
				out.close();
				item.delete();
				map.put("pimage", "upload/"+name);
			}
		}
		try {
			BeanUtils.populate(product, map);
		
			
//			private String pid;
			product.setPid(CommonUtils.getUUID().toString());
//			private String pimage;
//			private Date pdate;
			product.setPdate(new Date());
//			private int pflag;
			product.setPflag(0);
//			private Category category;
			Category category=new Category();
			//category不需要封装完全，因为product只用到了Cid 这一个属性
			category.setCid((String) map.get("cid"));
			product.setCategory(category);
			//将product传递给service层
			AdminServiceImpl service=new AdminServiceImpl();
			boolean addSuccess=service.addProduct(product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//是否封装数据完全
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package br.com.mvc.controller.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import br.com.mvc.controller.DAO.MarcaDAO;
import br.com.mvc.model.Marca;

@WebServlet("/SrvMarca")
public class SrvMarca extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MarcaDAO objDAO = new MarcaDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type");
		Marca obj;
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	    
		if (type == null) {
			return;
		}
		if (type.equals("find")) {
			out.print(objDAO.find(Integer.parseInt(request.getParameter("CD_MARCA"))).toJson().toString());
		}else if (type.equals("insert")) {
			obj = new Marca(new JSONObject(request.getParameter("OBJ")));
			out.print(objDAO.insert(obj));
			out.flush();
		}else if (type.equals("delete")){
			out.print(objDAO.delete(Integer.parseInt(request.getParameter("CD_MARCA"))));
			out.flush();
		}else if(type.equals("update")) {
			obj = new Marca(new JSONObject(request.getParameter("OBJ")));
			out.print(objDAO.update(obj));
			out.flush();
		}else if(type.equals("select")) {
			
			JSONObject json = new JSONObject();
			for (Marca m : objDAO.select(request.getParameter("WHERE"))) {
				json.append("Obj", m.toJson());
			};
			out.print(json.toString());
			out.flush();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

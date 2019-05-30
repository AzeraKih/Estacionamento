package br.com.mvc.controller.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import br.com.mvc.controller.DAO.TipoEstadiaDAO;
import br.com.mvc.model.TipoEstadia;

@WebServlet("/SrvTipoEstadia")
public class SrvTipoEstadia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	TipoEstadiaDAO objDAO = new TipoEstadiaDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type");
		TipoEstadia obj;
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	    
		if (type == null) {
			return;
		}
		if (type.equals("find")) {
			out.print(objDAO.find(Integer.parseInt(request.getParameter("CD_TIPO_ESTADIA"))).toJson().toString());
		}else if (type.equals("insert")) {
			obj = new TipoEstadia(new JSONObject(request.getParameter("OBJ")));
			out.print(objDAO.insert(obj));
			out.flush();
		}else if (type.equals("delete")){
			out.print(objDAO.delete(Integer.parseInt(request.getParameter("CD_TIPO_ESTADIA"))));
			out.flush();
		}else if(type.equals("update")) {
			obj = new TipoEstadia(new JSONObject(request.getParameter("OBJ")));
			out.print(objDAO.update(obj));
			out.flush();
		}else if(type.equals("select")) {
			
			JSONObject json = new JSONObject();
			for (TipoEstadia t : objDAO.select(request.getParameter("WHERE"))) {
				json.append("Obj", t.toJson());
			};
			out.print(json.toString());
			out.flush();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

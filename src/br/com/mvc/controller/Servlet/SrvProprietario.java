package br.com.mvc.controller.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import br.com.mvc.controller.DAO.ProprietarioDAO;
import br.com.mvc.model.Proprietario;

@WebServlet("/SrvProprietario")
public class SrvProprietario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProprietarioDAO objDAO = new ProprietarioDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type");
		Proprietario obj;
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	    
		if (type == null) {
			return;
		}
		if (type.equals("find")) {
			out.print(objDAO.find(Integer.parseInt(request.getParameter("CD_PROPRIETARIO"))).toJson().toString());
		}else if (type.equals("insert")) {
			obj = new Proprietario(new JSONObject(request.getParameter("OBJ")));
			out.print(objDAO.insert(obj));
			out.flush();
		}else if (type.equals("delete")){
			out.print(objDAO.delete(Integer.parseInt(request.getParameter("CD_PROPRIETARIO"))));
			out.flush();
		}else if(type.equals("update")) {
			obj = new Proprietario(new JSONObject(request.getParameter("OBJ")));
			out.print(objDAO.update(obj));
			out.flush();
		}else if(type.equals("select")) {
			
			JSONObject json = new JSONObject();
			for (Proprietario p : objDAO.select(request.getParameter("WHERE"))) {
				json.append("Obj", p.toJson());
			};
			out.print(json.toString());
			out.flush();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

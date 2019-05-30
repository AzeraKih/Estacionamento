package br.com.mvc.controller.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.mvc.controller.DAO.TabelaTipoDAO;
import br.com.mvc.model.TabelaTipo;

@WebServlet("/SrvTabelaTipo")
public class SrvTabelaTipo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	TabelaTipoDAO objDAO = new TabelaTipoDAO();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type");
		TabelaTipo obj;
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	    
		if (type == null) {
			return;
		}
		
		if (type.equals("insert")) {
			try {
				obj = new TabelaTipo(new JSONObject(request.getParameter("OBJ")));
				out.print(objDAO.insert(obj));
			}catch (JSONException ex) {
				out.print(ex.getMessage());
			}
			out.flush();
		}else if (type.equals("delete")){
			out.print(objDAO.delete(Integer.parseInt(request.getParameter("CD_TABELA_TIPO"))));
			out.flush();
		}else if(type.equals("update")) {
			
			try {
				obj = new TabelaTipo(new JSONObject(request.getParameter("OBJ")));
				out.print(objDAO.update(obj));
			}catch (JSONException ex) {
				out.print(ex.getMessage());
			}
			out.flush();
		}else if(type.equals("select")) {

			JSONObject json = new JSONObject();
			for (TabelaTipo m : objDAO.select(request.getParameter("WHERE"))) {
				json.append("Obj", m.toJson());
			};
			out.print(json.toString());
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

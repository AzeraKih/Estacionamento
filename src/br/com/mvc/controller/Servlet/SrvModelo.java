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

import br.com.mvc.controller.DAO.MarcaDAO;
import br.com.mvc.controller.DAO.ModeloDAO;
import br.com.mvc.controller.DAO.TabelaTipoDAO;
import br.com.mvc.model.Modelo;

@WebServlet("/SrvModelo")
public class SrvModelo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ModeloDAO objDAO = new ModeloDAO();
	MarcaDAO marcaDAO = new MarcaDAO();
	TabelaTipoDAO tabelaDAO = new TabelaTipoDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type");
		Modelo obj;
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	    
		if (type == null) {
			return;
		}
		
		JSONObject jsonObj;
		
		if (type.equals("insert")) {
			
			
			try {
				jsonObj = new JSONObject(request.getParameter("OBJ"));
				obj = new Modelo(jsonObj, 
								marcaDAO.find(jsonObj.getInt("CD_MARCA")), 
								tabelaDAO.find(jsonObj.getInt("CD_TABELA_TIPO")));
				out.print(objDAO.insert(obj));
			}catch (JSONException ex) {
				out.print(ex.getMessage());
			}
			out.flush();
		}else if (type.equals("delete")){
			out.print(objDAO.delete(Integer.parseInt(request.getParameter("CD_MODELO"))));
			out.flush();
		}else if(type.equals("update")) {
			
			try {
				jsonObj = new JSONObject(request.getParameter("OBJ"));
				obj = new Modelo(jsonObj, 
						marcaDAO.find(jsonObj.getInt("CD_MARCA")), 
						tabelaDAO.find(jsonObj.getInt("CD_TABELA_TIPO")));
				out.print(objDAO.update(obj));
			}catch (JSONException ex) {
				out.print(ex.getMessage());
			}
			out.flush();
		}else if(type.equals("select")) {
			
			JSONObject json = new JSONObject();
			for (Modelo m : objDAO.select(request.getParameter("WHERE"))) {
				json.append("Obj", m.toJson());
			};
			out.print(json.toString());
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

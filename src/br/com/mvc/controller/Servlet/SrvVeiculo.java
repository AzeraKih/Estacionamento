package br.com.mvc.controller.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import br.com.mvc.controller.DAO.ModeloDAO;
import br.com.mvc.controller.DAO.ProprietarioDAO;
import br.com.mvc.controller.DAO.VeiculoDAO;
import br.com.mvc.model.Modelo;
import br.com.mvc.model.Proprietario;
import br.com.mvc.model.Veiculo;

@WebServlet("/SrvVeiculo")
public class SrvVeiculo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	VeiculoDAO objDAO = new VeiculoDAO();
	ProprietarioDAO proDAO = new ProprietarioDAO();
	ModeloDAO moDAO = new ModeloDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type");
		Veiculo obj;
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	    
		if (type == null) {
			return;
		}
		if (type.equals("find")) {
			out.print(objDAO.find(Integer.parseInt(request.getParameter("CD_Veiculo"))).toJson().toString());
		}else if (type.equals("insert")) {
			
			JSONObject jo = new JSONObject(request.getParameter("OBJ"));
			Modelo m = moDAO.find(jo.getInt("CD_MODELO"));
			Proprietario p = proDAO.find(jo.getInt("CD_PROPRIETARIO"));
			
			obj = new Veiculo(jo, m, p);			
			out.print(objDAO.insert(obj));
			out.flush();
		}else if (type.equals("delete")){
			out.print(objDAO.delete(Integer.parseInt(request.getParameter("CD_Veiculo"))));
			out.flush();
		}else if(type.equals("update")) {
			
			JSONObject jo = new JSONObject(request.getParameter("OBJ"));
			Modelo m = moDAO.find(jo.getInt("CD_MODELO"));
			Proprietario p = proDAO.find(jo.getInt("CD_PROPRIETARIO"));
			
			obj = new Veiculo(jo, m, p);		
			
			out.print(objDAO.update(obj));
			out.flush();
		}else if(type.equals("select")) {
			
			JSONObject json = new JSONObject();
			for (Veiculo v : objDAO.select(request.getParameter("WHERE"))) {
				json.append("Obj", v.toJson());
			};
			out.print(json.toString());
			out.flush();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

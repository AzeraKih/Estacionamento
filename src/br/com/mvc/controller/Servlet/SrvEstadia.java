package br.com.mvc.controller.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.mvc.controller.DAO.EstadiaDAO;
import br.com.mvc.controller.DAO.TipoEstadiaDAO;
import br.com.mvc.controller.DAO.VeiculoDAO;
import br.com.mvc.model.Estadia;
import br.com.mvc.model.TipoEstadia;
import br.com.mvc.model.Veiculo;

@WebServlet("/SrvEstadia")
public class SrvEstadia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	EstadiaDAO objDAO = new EstadiaDAO();
	TipoEstadiaDAO teDAO = new TipoEstadiaDAO();
	VeiculoDAO veDAO = new VeiculoDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("type");
		Estadia obj;
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	    
		if (type == null) {
			return;
		}
		if (type.equals("find")) {
			out.print(objDAO.find(Integer.parseInt(request.getParameter("CD_ESTADIA"))).toJson().toString());
		}else if (type.equals("insert")) {
			
			JSONObject jo = new JSONObject(request.getParameter("OBJ"));	
			Veiculo v = veDAO.find(jo.getInt("CD_VEICULO"));
			TipoEstadia te = teDAO.find(jo.getInt("CD_TIPO_ESTADIA"));
			try {
				obj = new Estadia(jo, v, te);
				out.print(objDAO.insert(obj));
			} catch (JSONException | ParseException e) {
				out.print(e.getMessage());
			}
			
			out.flush();
		}else if (type.equals("delete")){
			out.print(objDAO.delete(Integer.parseInt(request.getParameter("CD_ESTADIA"))));
			out.flush();
		}else if(type.equals("update")) {
			
			JSONObject jo = new JSONObject(request.getParameter("OBJ"));	
			Veiculo v = veDAO.find(jo.getInt("CD_VEICULO"));
			TipoEstadia te = teDAO.find(jo.getInt("CD_TIPO_ESTADIA"));
			try {
				obj = new Estadia(jo, v, te);
				out.print(objDAO.update(obj));
			} catch (JSONException | ParseException e) {
				out.print(e.getMessage());
			}
			
			out.flush();
		}else if(type.equals("select")) {
			
			JSONObject json = new JSONObject();
			for (Estadia e : objDAO.select(request.getParameter("WHERE"))) {
				json.append("Obj", e.toJson());
			};
			out.print(json.toString());
			out.flush();
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

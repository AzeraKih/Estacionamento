package br.com.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "TIPO_ESTADIA")
public class TipoEstadia {	
//	 Name                                      Null?    Type
//	 ----------------------------------------- -------- ----------------------------
//	 CD_TIPO_ESTADIA                           NOT NULL NUMBER(9)
//	 DS_TIPO_ESTADIA                           NOT NULL VARCHAR2(90)
	
	public TipoEstadia() {}
	public TipoEstadia(int id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}
	
	public TipoEstadia(JSONObject jo) {
		
		if(jo.has("CD_TIPO_ESTADIA")) {
			this.id = jo.getInt("CD_TIPO_ESTADIA");
		}
		this.descricao = jo.getString("DS_TIPO_ESTADIA");
	}
	
	@Id
	@Column(name = "CD_TIPO_ESTADIA")
	private int id;
	
	@Column(name = "DS_TIPO_ESTADIA")
	private String descricao;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String toJson() {
		JSONObject propJson = new JSONObject();
		propJson.put("CD_TIPO_ESTADIA", this.id);
		propJson.put("DS_TIPO_ESTADIA", this.descricao);
		return propJson.toString();
	}
	
}

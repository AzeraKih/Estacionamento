package br.com.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "MARCA")
public class Marca {
	
//	 Name                                      Null?    Type
//	 ----------------------------------------- -------- ----------------------------
//	 CD_MARCA                                  NOT NULL NUMBER(9)
//	 DS_MARCA                                  NOT NULL VARCHAR2(90)
	
	public Marca() {}
	public Marca(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public Marca(JSONObject jo) {
		
		if(jo.has("CD_MARCA")) {
			this.id = jo.getInt("CD_MARCA");
		}
		this.descricao = jo.getString("DS_MARCA");
	}

	@Id
	@Column(name = "CD_MARCA")
	private int id;
	
	@Column(name = "DS_MARCA")
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
	
	public JSONObject toJson() {
		JSONObject propJson = new JSONObject();
		propJson.put("CD_MARCA", this.id);
		propJson.put("DS_MARCA", this.descricao);
		return propJson;
	}
	
	
}

package br.com.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "VEICULO")
public class Veiculo {
	
//	 Name                                      Null?    Type
//	 ----------------------------------------- -------- ----------------------------
//	 CD_VEICULO                                NOT NULL NUMBER(9)
//	 DS_COR                                             VARCHAR2(25)
//	 DS_PLACA                                  NOT NULL VARCHAR2(10)
//	 CD_MODELO                                 NOT NULL NUMBER(9)
// 	 CD_PROPRIETARIO						   NOT NULL NUMBER(9)		
	
	
	public Veiculo() {}
	public Veiculo(int id, String cor, String placa, Modelo modelo) {
		super();
		this.id = id;
		this.cor = cor;
		this.placa = placa;
		this.modelo = modelo;
	}
	
	public Veiculo(JSONObject jo, Modelo m, Proprietario p) {
		if(jo.has("CD_VEICULO")) {
			this.id = jo.getInt("CD_VEICULO");
		}
		this.placa = jo.getString("DS_PLACA");
		this.cor = jo.getString("DS_COR");
		this.modelo = m;
		this.proprietario = p;
	}
	

	@Id
	@Column(name = "CD_VEICULO")
	private int id;
	
	@Column(name = "DS_COR")
	private String cor;
	
	@Column(name = "DS_PLACA")
	private String placa;
	
	@ManyToOne
	@JoinColumn(name = "CD_MODELO")
	private Modelo modelo;
	
	@ManyToOne
	@JoinColumn(name = "CD_PROPRIETARIO")
	private Proprietario proprietario;
	
	public Proprietario getProprietario() {
		return proprietario;
	}
	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public Modelo getModelo() {
		return modelo;
	}
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	
	public JSONObject toJson() {
		JSONObject propJson = new JSONObject();
		propJson.put("CD_VEICULO", this.id);
		propJson.put("DS_COR", this.cor);
		propJson.put("DS_PLACA", this.placa);
		propJson.put("CD_MODELO", this.modelo.getId());
		propJson.put("DS_MODELO", this.modelo.getDescricao());
		propJson.put("CD_PROPRIETARIO", this.proprietario.getId());
		propJson.put("NM_PROPRIETARIO", this.proprietario.getNome());
		return propJson;
	}
	
}

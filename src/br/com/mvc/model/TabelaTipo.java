package br.com.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "TABELA_TIPO")
public class TabelaTipo {
	
//	 Name                                      Null?    Type
//	 ----------------------------------------- -------- ----------------------------
//	 CD_TABELA_TIPO                            NOT NULL NUMBER(9)
//	 PR_P15                                    NOT NULL NUMBER(5,2)
//	 PR_P30                                    NOT NULL NUMBER(5,2)
//	 PR_P60                                    NOT NULL NUMBER(5,2)
//	 PR_D15                                    NOT NULL NUMBER(5,2)
//	 PR_PDIA                                   NOT NULL NUMBER(5,2)
//	 PR_PMES                                            NUMBER(5,2)
//	 DS_TIPO_VEICULO                           NOT NULL VARCHAR2(90)
	
	public TabelaTipo(){}
	public TabelaTipo(int id, String tipo, double p15, double p30, double p60, double d15, double pdia, double pmes) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.p15 = p15;
		this.p30 = p30;
		this.p60 = p60;
		this.d15 = d15;
		this.pdia = pdia;
		this.pmes = pmes;
	}
	
	public TabelaTipo(JSONObject jo) {
		
		if(jo.has("CD_TABELA_TIPO")) {
			this.id = jo.getInt("CD_TABELA_TIPO");
		}
		this.tipo = jo.getString("DS_TIPO_VEICULO");
		this.p15 = jo.getDouble("PR_P15");
		this.p30 = jo.getDouble("PR_P30");
		this.p60 = jo.getDouble("PR_P60");
		this.d15 = jo.getDouble("PR_D15");
		this.pdia = jo.getDouble("PR_PDIA");
		this.pmes = jo.getDouble("PR_PMES");
	}
	
	@Id
	@Column(name = "CD_TABELA_TIPO")
	private int id;

	@Column(name = "DS_TIPO_VEICULO")
	private String tipo;

	@Column(name = "PR_P15")
	private double p15;

	@Column(name = "PR_P30")
	private double p30;

	@Column(name = "PR_P60")
	private double p60;

	@Column(name = "PR_D15")
	private double d15;

	@Column(name = "PR_PDIA")
	private double pdia;

	@Column(name = "PR_PMES")
	private double pmes;

	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getP15() {
		return p15;
	}
	public void setP15(double p15) {
		this.p15 = p15;
	}
	public double getP30() {
		return p30;
	}
	public void setP30(double p30) {
		this.p30 = p30;
	}
	public double getP60() {
		return p60;
	}
	public void setP60(double p60) {
		this.p60 = p60;
	}
	public double getD15() {
		return d15;
	}
	public void setD15(double d15) {
		this.d15 = d15;
	}
	public double getPdia() {
		return pdia;
	}
	public void setPdia(double pdia) {
		this.pdia = pdia;
	}
	public double getPmes() {
		return pmes;
	}
	public void setPmes(double pmes) {
		this.pmes = pmes;
	}
	
	public JSONObject toJson() {
		JSONObject propJson = new JSONObject();
		propJson.put("CD_TABELA_TIPO", this.id);
		propJson.put("DS_TIPO_VEICULO", this.tipo);
		propJson.put("PR_P15", this.p15);
		propJson.put("PR_P30", this.p30);
		propJson.put("PR_P60", this.p60);
		propJson.put("PR_D15", this.d15);
		propJson.put("PR_PDIA", this.pdia);
		propJson.put("PR_PMES", this.pmes);
		return propJson;
	}
	
}

package br.com.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "MODELO")
public class Modelo {
	
//	 Name                                      Null?    Type
//	 ----------------------------------------- -------- ----------------------------
//	 CD_MODELO                                 NOT NULL NUMBER(9)
//	 DS_MODELO                                 NOT NULL VARCHAR2(90)
//	 CD_MARCA                                  NOT NULL NUMBER(9)
//	 CD_TABELA_TIPO                            NOT NULL NUMBER(9)
			
	public Modelo() {}
	public Modelo(int id, String descricao, int ano, TabelaTipo tabela, Marca marca) {
		this.id = id;
		this.descricao = descricao;
		this.ano = ano;
		this.tabela = tabela;
		this.marca = marca;
	}
	public Modelo(JSONObject jo,  Marca m, TabelaTipo t) {
		
		if(jo.has("CD_MODELO")) {
			this.id = jo.getInt("CD_MODELO");
		}
		this.ano = jo.getInt("DT_ANO");
		this.descricao = jo.getString("DS_MODELO");
		this.marca = m;
		this.tabela = t;
	}

	@Id
	@Column(name = "CD_MODELO")
	private int id;
	
	@Column(name = "DS_MODELO")
	private String descricao;
	
	@Column(name = "DT_ANO")
	private int ano;
	
	@ManyToOne
	@JoinColumn(name = "CD_TABELA_TIPO")
	private TabelaTipo tabela;
	
	@ManyToOne
	@JoinColumn(name = "CD_MARCA")
	private Marca marca;
	
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
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
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public TabelaTipo getTabela() {
		return tabela;
	}
	public void setTabela(TabelaTipo tabela) {
		this.tabela = tabela;
	}
	
	public JSONObject toJson() {
		JSONObject propJson = new JSONObject();
		propJson.put("CD_MODELO", this.id); 
		propJson.put("DS_MODELO", this.descricao);
		propJson.put("DT_ANO", this.ano);
		propJson.put("CD_MARCA", this.marca.getId());
		propJson.put("DS_MARCA", this.marca.getDescricao());
		propJson.put("CD_TABELA_TIPO", this.tabela.getId()); 
		propJson.put("DS_TIPO_VEICULO", this.tabela.getTipo()); 
		return propJson;
	}
	
}

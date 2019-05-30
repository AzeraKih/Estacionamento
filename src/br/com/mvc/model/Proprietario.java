package br.com.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "PROPRIETARIO")
public class Proprietario {
	
//	 Name                                      Null?    Type
//   ----------------------------------------- -------- ----------------------------
//	 CD_PROPRIETARIO                           NOT NULL NUMBER(9)
//	 NM_PROPRIETARIO                           NOT NULL VARCHAR2(90)
//	 TEL_PROPRIETARIO                          NOT NULL NUMBER(14)
//	 DS_ENDERECO                               NOT NULL VARCHAR2(255)
//	 NR_CPF                                    NOT NULL NUMBER(12)
	
	public Proprietario() {}
	public Proprietario(int id, String nome, int telefone, String endereco, int cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.cpf = cpf;
	}
	
	public Proprietario(JSONObject jo){		
		this.id = jo.getInt("CD_PROPRIETARIO");
		this.nome = jo.getString("NM_PROPRIETARIO");
		this.telefone = jo.getInt("TEL_PROPRIETARIO");
		this.endereco = jo.getString("DS_ENDERECO");
		this.cpf = jo.getInt("NR_CPF");
	}
	
	
	@Id
	@Column(name = "CD_PROPRIETARIO")
	private int id;
	
	@Column(name = "NM_PROPRIETARIO")
	private String nome;
	
	@Column(name = "TEL_PROPRIETARIO")
	private int telefone;
	
	@Column(name = "DS_ENDERECO")
	private String endereco;
	
	@Column(name = "NR_CPF")
	private int cpf;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public int getCpf() {
		return cpf;
	}
	public void setCpf(int cpf) {
		this.cpf = cpf;
	}
	public int getTelefone() {
		return telefone;
	}
	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}
	public String toJson() {
		JSONObject propJson = new JSONObject();
		propJson.put("CD_PROPRIETARIO", this.id);
		propJson.put("NM_PROPRIETARIO", this.nome);
		propJson.put("TEL_PROPRIETARIO", this.telefone);
		propJson.put("DS_ENDERECO", this.endereco);
		propJson.put("NR_CPF", this.cpf);
		return propJson.toString();
	}
	
	
}

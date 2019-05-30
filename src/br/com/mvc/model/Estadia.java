package br.com.mvc.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
@Table(name = "ESTADIA")
public class Estadia{
	
//	 Name                                      Null?    Type
//	 ----------------------------------------- -------- ----------------------------
//	 CD_ESTADIA                                NOT NULL NUMBER(9)
//	 HR_ENTRADA                                NOT NULL DATE
//	 HR_SAIDA                                  NOT NULL DATE
//	 CD_TIPO_ESTADIA                                    NUMBER(9)
//	 CD_VEICULO                                         NUMBER(9)
	
	public Estadia() {}
	public Estadia(int id, Date horaEntrada, Date horaSaida, TipoEstadia tipo, Veiculo veiculo) {
		super();
		this.id = id;
		this.horaEntrada = horaEntrada;
		this.horaSaida = horaSaida;
		this.tipo = tipo;
		this.veiculo = veiculo;
	}
	public Estadia(JSONObject jo, Veiculo v, TipoEstadia t) throws JSONException, ParseException {
		
		SimpleDateFormat toDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");  
		  
		if(jo.has("CD_ESTADIA")) {
			this.id = jo.getInt("CD_ESTADIA");
		}
		this.horaEntrada = toDate.parse(jo.getString("HR_ENTRADA"));
		this.horaSaida = toDate.parse(jo.getString("HR_SAIDA"));
		this.veiculo = v;
		this.tipo = t;
	}

	@Id
	@Column(name = "CD_ESTADIA")
	private int id;
	
	@Column(name = "HR_ENTRADA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaEntrada;
	
	@Column(name = "HR_SAIDA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaSaida;
	
	@ManyToOne
	@JoinColumn(name = "CD_TIPO_ESTADIA")
	private TipoEstadia tipo;
	
	@ManyToOne
	@JoinColumn(name = "CD_VEICULO")
	private Veiculo veiculo;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public Date getHoraSaida() {
		return horaSaida;
	}
	public void setHoraSaida(Date horaSaida) {
		this.horaSaida = horaSaida;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	public TipoEstadia getTipo() {
		return tipo;
	}
	public void setTipo(TipoEstadia tipo) {
		this.tipo = tipo;
	}
	
	public JSONObject toJson() {
		JSONObject propJson = new JSONObject();
		propJson.put("CD_ESTADIA", this.id);
		propJson.put("HR_ENTRADA", this.horaEntrada.toString());
		propJson.put("HR_SAIDA", this.horaSaida.toString());
		propJson.put("CD_TIPO_ESTADIA", this.tipo.getId());
		propJson.put("DS_TIPO_ESTADIA", this.tipo.getDescricao());
		propJson.put("CD_VEICULO", this.veiculo.getId());
		propJson.put("DS_MODELO", this.veiculo.getModelo().getDescricao());
		return propJson;
	}
	
}

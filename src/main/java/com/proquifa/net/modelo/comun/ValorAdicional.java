package  com.proquifa.net.modelo.comun;
/**
 * @author bryan.magana
 *
 */
public class ValorAdicional {


	private Long id;
	
	private String nombre;
	private String valor;
	private String valorSecundario;
	private String tipo;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	/**
	 * @return the valorSecundario
	 */
	public String getValorSecundario() {
		return valorSecundario;
	}
	/**
	 * @param valorSecundario the valorSecundario to set
	 */
	public void setValorSecundario(String valorSecundario) {
		this.valorSecundario = valorSecundario;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}

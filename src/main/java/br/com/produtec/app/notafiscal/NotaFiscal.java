package br.com.produtec.app.notafiscal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.TypeDef;

import br.com.produtec.app.service.WebServiceReceita;

import com.google.common.base.Objects;

@TypeDef(name = "estadoNotaFiscal", typeClass = EstadoNotaFiscalUserType.class, defaultForType = EstadoNotaFiscal.class)
@Entity
public class NotaFiscal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Version
	Integer version;

	EstadoNotaFiscal estado = EstadoNotaFiscal.FATURADA;

	@NotNull
	private Integer numero;

	NotaFiscal() {
	}

	private NotaFiscal(Integer numero) {
		this.numero = numero;
	}

	public static NotaFiscal newNotaFiscal(Integer numero) {
		checkNotNull(numero);
		checkArgument(numero > 0);
		return new NotaFiscal(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotaFiscal) {
			NotaFiscal other = (NotaFiscal) obj;
			return Objects.equal(this.numero, other.numero);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.numero);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("numero", numero).toString();
	}

	public String getMensagem() {
		return estado.accept(new MensagemEstadoNotaFiscalVisitor());
	}

	public int getCodigoNaReceita() {
		return estado.accept(new CodigoReceitaEstadoNotaFiscalVisitor());
	}

	public void enviar(WebServiceReceita webServiceReceita) {
		//codigo encapsulado
		// Tell, don't ask

		// Mande, não pergunte
	}

}
package uea.pagamentos_api.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pagamentosapi")
public class PagamentosApiProperty {
	private List<String> originPermitida;

	private final Seguranca seguranca = new Seguranca();

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public List<String> getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(List<String> originPermitida) {
		this.originPermitida = originPermitida;
	}

	public static class Seguranca {

		private List<String> redirectsPermitidos;
		private String authServerUrl;

		public List<String> getRedirectsPermitidos() {
			return redirectsPermitidos;
		}

		public void setRedirectsPermitidos(List<String> redirectsPermitidos) {
			this.redirectsPermitidos = redirectsPermitidos;
		}

		public String getAuthServerUrl() {
			return authServerUrl;
		}

		public void setAuthServerUrl(String authServerUrl) {
			this.authServerUrl = authServerUrl;
		}
	}

}


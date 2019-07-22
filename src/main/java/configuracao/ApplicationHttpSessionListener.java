package configuracao;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ApplicationHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		// Setando 15 minutos de timeout da sessão
		sessionEvent.getSession().setMaxInactiveInterval(15*60); //in seconds
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {}

}

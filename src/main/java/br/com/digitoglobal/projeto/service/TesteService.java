package br.com.digitoglobal.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class TesteService {
	
	@Value("${spring.profiles.active}")
	private String valor;
	
	@Autowired Environment environment;
	
	public TesteService() {
		System.out.println("entrou: "+valor);
	}
	
	public String getValor() {
		return valor;
	}

}

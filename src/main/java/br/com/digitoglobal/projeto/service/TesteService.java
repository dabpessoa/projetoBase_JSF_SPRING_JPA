package br.com.digitoglobal.projeto.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TesteService {
	
	@Value("${spring.profiles.active}")
	private String valor;
	
	public TesteService() {
		System.out.println("entrou: "+valor);
	}

}

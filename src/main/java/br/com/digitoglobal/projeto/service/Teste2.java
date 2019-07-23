package br.com.digitoglobal.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class Teste2 {
	
	@Autowired private TesteService testeService;
	@Autowired private Environment environment;	
	
	public Teste2() {
		System.out.println("Entrou..."+testeService);
	}
	
	public TesteService getTesteService() {
		return testeService;
	}

}

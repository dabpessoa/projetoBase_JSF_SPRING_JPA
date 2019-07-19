package br.com.digitoglobal.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Teste2 {
	
	@Autowired
	private TesteService testeService;
	
	public Teste2() {
		System.out.println("Entrou..."+testeService);
	}
	
	public TesteService getTesteService() {
		return testeService;
	}

}

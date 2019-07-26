package br.com.digitoglobal.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import br.com.digitoglobal.projeto.service.Teste2;
import br.com.digitoglobal.projeto.util.jsf.view.ViewController;

@ViewController
public class MeuController {
	
	@Autowired private Teste2 teste;
	@Value("${valor}") private String valor;
	@Value("${valor2}") private String valor2;
	
	public MeuController() {
		System.out.println("Entrou mo meuController");
	}
	
	public String getProfile() {
		return teste.getTesteService().getValor();
	}

}

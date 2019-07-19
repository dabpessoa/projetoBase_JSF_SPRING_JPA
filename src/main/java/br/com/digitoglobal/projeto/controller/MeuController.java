package br.com.digitoglobal.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.digitoglobal.projeto.service.Teste2;

@Controller
public class MeuController {
	
	@Autowired private Teste2 teste;
	
	public MeuController() {
		System.out.println("Entrou mo meuController");
	}
	
	public String getProfile() {
		return teste.getTesteService().getValor();
	}

}

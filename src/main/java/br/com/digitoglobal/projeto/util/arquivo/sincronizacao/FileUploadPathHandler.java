package br.com.digitoglobal.projeto.util.arquivo.sincronizacao;

import org.springframework.core.env.Environment;

import br.com.digitoglobal.projeto.util.SpringContextUtils;

public interface FileUploadPathHandler {
	
	public static final String ARQUIVO_UPLOAD_CAMINHO = "/upload";
	public static final String NOME_APLICACAO = "aplicacao";

    String getCaminhoRelativo();
    Long getCodigoEntidadePai();
    void setNomeArquivo(String nome);
    String getNomeArquivo();

    /**
     * Caminho raíz onde serão salvos todos os arquivos desse sistema.
     * @return
     */
    default String getCaminhoRaiz() {
    	Environment environment = SpringContextUtils.getBean(Environment.class);
        return environment.getProperty(ARQUIVO_UPLOAD_CAMINHO)+"/"+environment.getProperty(NOME_APLICACAO);
    }

    /**
     * Caminho base para salvar arquivos de uma determinada funcionalidade.
     * @return
     */
    default String gerarCaminhoBase() {
        return getCaminhoRaiz()+getCaminhoRelativo()+"/"+getCodigoEntidadePai();
    }

    /**
     * Caminho completo de um arquivo específico
     * @return
     */
    default String gerarCaminhoCompleto() {
        return gerarCaminhoBase()+"/"+getNomeArquivo();
    }

}

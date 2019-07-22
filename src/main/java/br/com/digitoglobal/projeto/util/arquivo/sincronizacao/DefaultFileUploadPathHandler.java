package br.com.digitoglobal.projeto.util.arquivo.sincronizacao;

public class DefaultFileUploadPathHandler implements FileUploadPathHandler {

    private String caminhoRelativo;
    private Long codigoEntidadePai;
    private String nomeArquivo;

    public DefaultFileUploadPathHandler(String caminhoRelativo, Long codigoEntidadePai) {
        this(caminhoRelativo, null, codigoEntidadePai);
    }

    public DefaultFileUploadPathHandler(String caminhoRelativo, String nomeArquivo, Long codigoEntidadePai) {
        this.caminhoRelativo = caminhoRelativo;
        this.nomeArquivo = nomeArquivo;
        this.codigoEntidadePai = codigoEntidadePai;
    }

    @Override
    public Long getCodigoEntidadePai() {
        return codigoEntidadePai;
    }

    @Override
    public String getCaminhoRelativo() {
        return caminhoRelativo;
    }

    @Override
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    @Override
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

}

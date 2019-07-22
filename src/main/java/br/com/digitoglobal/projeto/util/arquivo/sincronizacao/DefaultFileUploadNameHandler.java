package br.com.digitoglobal.projeto.util.arquivo.sincronizacao;

import java.util.Date;

import br.com.digitoglobal.projeto.bean.arquivo.Arquivavel;
import br.com.digitoglobal.projeto.bean.model.BaseEntity;
import br.com.digitoglobal.projeto.util.DateUtils;

public class DefaultFileUploadNameHandler<T extends Arquivavel, E extends BaseEntity> implements FileUploadNameHandler {

    private T arquivavel;
    private E entidadePai;

    public DefaultFileUploadNameHandler(T arquivavel, E entidadePai) {
        this.arquivavel = arquivavel;
        this.entidadePai = entidadePai;
    }

    @Override
    public String gerarNomeArquivo() {
        return entidadePai.getId() + "_" + arquivavel.getNome().substring(0, arquivavel.getNome().lastIndexOf("."))+"_"+DateUtils.format(new Date(), "yyyyMMdd_HHmmssSSS")+arquivavel.getNome().substring(arquivavel.getNome().lastIndexOf("."));
    }

}

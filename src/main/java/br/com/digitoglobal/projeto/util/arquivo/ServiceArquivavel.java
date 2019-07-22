package br.com.digitoglobal.projeto.util.arquivo;

import br.com.digitoglobal.projeto.bean.model.BaseEntity;

public interface ServiceArquivavel {

    void  removerArquivosPorEntidadePai(BaseEntity entity);

}

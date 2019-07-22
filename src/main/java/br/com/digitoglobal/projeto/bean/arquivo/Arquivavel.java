package br.com.digitoglobal.projeto.bean.arquivo;

import java.io.File;
import java.io.IOException;

import br.com.digitoglobal.projeto.util.FileUtils;
import br.com.digitoglobal.projeto.util.arquivo.sincronizacao.FileUploadNameHandler;

public interface Arquivavel {

    Long getCodigo();
    String getNome();
    String getNomeFisico();
    String getCaminho();
    String getMimeType();
    Long getTamanho();
    byte[] getBytes();
    void setBytes(byte[] bytes);
    FileUploadNameHandler getFileUploadNameHandler();
    void setFileUploadNameHandler(FileUploadNameHandler fileUploadNameHandler);

    default void carregarBytesDoDisco() throws IOException {
        carregarBytesDoDisco(false);
    }

    default void carregarBytesDoDisco(boolean force) throws IOException {
        if (!force && isBytesPreenchido()) return;
        setBytes(FileUtils.readBytes(new File(getCaminho())));
    }

    default void escreverBytesNoDisco() throws IOException {
        FileUtils.write(new File(getCaminho()), getBytes());
    }

    default void apagarDoDisco() {
        FileUtils.deleteQuietly(getCaminho());
    }

    default String gerarNome() {
        return getFileUploadNameHandler().gerarNomeArquivo();
    }

    default boolean isBytesPreenchido() {
        return getBytes() != null;
    }

}

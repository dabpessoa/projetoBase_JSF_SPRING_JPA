package br.com.digitoglobal.projeto.bean.arquivo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import br.com.digitoglobal.projeto.bean.model.BaseEntity;
import br.com.digitoglobal.projeto.util.arquivo.sincronizacao.DefaultFileUploadNameHandler;
import br.com.digitoglobal.projeto.util.arquivo.sincronizacao.FileUploadNameHandler;
import br.com.digitoglobal.projeto.util.arquivo.sincronizacao.FileUploadPathHandler;
import br.com.digitoglobal.projeto.util.optionalList.OptionalModelEntity;

@MappedSuperclass
public abstract class ArquivoBasico extends OptionalModelEntity implements Arquivavel {

    private String nome;

    @Column(name = "nome_fisico")
    private String nomeFisico;

    private String caminho;

    private Long tamanho;

    @Column(name = "mime_type")
    private String mimeType;

    private byte[] bytes;

    @Column(name = "dt_cadastro")
    private Date dataCadastro;

    @Transient
    private FileUploadNameHandler fileUploadNameHandler;

    @Transient
    private FileUploadPathHandler fileUploadPathHandler;

    @Transient
    private byte[] bytesMiniatura;

    public ArquivoBasico() {}

    public ArquivoBasico(Arquivavel arquivavel, BaseEntity entidadepai) {
        setFileUploadNameHandler(new DefaultFileUploadNameHandler(arquivavel, entidadepai));
    }

    public ArquivoBasico(String nome, String caminho, byte[] bytes) {
        this.nome = nome;
        this.caminho = caminho;
        this.bytes = bytes;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNomeFisico() {
        return nomeFisico;
    }

    public void setNomeFisico(String nomeFisico) {
        this.nomeFisico = nomeFisico;
    }

    public void setNomeFisico(FileUploadNameHandler fileNameHandler) {
        setFileUploadNameHandler(fileNameHandler);
        setNomeFisico(gerarNome());
    }

    @Override
    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public void setCaminho(FileUploadPathHandler fileUploadPathHandler) {
        setFileUploadPathHandler(fileUploadPathHandler);
        setCaminho(fileUploadPathHandler.gerarCaminhoCompleto());
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public FileUploadNameHandler getFileUploadNameHandler() {
        return fileUploadNameHandler;
    }

    public void setFileUploadNameHandler(FileUploadNameHandler fileUploadNameHandler) {
        this.fileUploadNameHandler = fileUploadNameHandler;
    }

    public FileUploadPathHandler getFileUploadPathHandler() {
        return fileUploadPathHandler;
    }

    public void setFileUploadPathHandler(FileUploadPathHandler fileUploadPathHandler) {
        this.fileUploadPathHandler = fileUploadPathHandler;
    }

    public byte[] getBytesMiniatura() {
        return bytesMiniatura;
    }

    public void setBytesMiniatura(byte[] bytesMiniatura) {
        this.bytesMiniatura = bytesMiniatura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArquivoBasico)) return false;
        if (!super.equals(o)) return false;

        ArquivoBasico that = (ArquivoBasico) o;

        return nome != null ? nome.equals(that.nome) : that.nome == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ArquivoBasico{" +
                "nome='" + nome + '\'' +
                ", nomeFisico='" + nomeFisico + '\'' +
                ", caminho='" + caminho + '\'' +
                ", tamanho=" + tamanho +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }

}

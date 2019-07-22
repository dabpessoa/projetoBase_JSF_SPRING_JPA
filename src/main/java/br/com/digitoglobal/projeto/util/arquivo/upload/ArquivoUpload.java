package br.com.digitoglobal.projeto.util.arquivo.upload;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ArquivoUpload {

    private String nome;
    private byte[] bytes;
    private Long size;
    private InputStream inputStream;
    private String contentType;
    private String tempPath;

    public ArquivoUpload() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public static List<ArquivoUpload> parse(List<MultipartItem> multipartItems) {
        List<ArquivoUpload> arquivos = new ArrayList<>();
        if (multipartItems != null) {
            for (MultipartItem multipartItem : multipartItems) {
                ArquivoUpload arquivo = new ArquivoUpload();
                arquivo.setNome(multipartItem.getName());
                arquivo.setSize(multipartItem.getPart().getSize());

                InputStream inputStream = null;
                try {
                    inputStream = multipartItem.getPart().getInputStream();
                } catch (IOException e) {/*do nothing*/}

                String path = null;
                if (inputStream != null) {
                    try {
                        Field f = inputStream.getClass().getDeclaredField("path");
                        f.setAccessible(true);
                        path = (String) f.get(inputStream);
                    } catch (NoSuchFieldException e) {/*do nothing*/} catch (IllegalAccessException e) {/*do nothing*/}
                }

                arquivo.setInputStream(inputStream);
                arquivo.setTempPath(path);
                arquivo.setContentType(Optional.ofNullable(multipartItem.getPart()).map(p -> p.getContentType()).orElse(null));
                arquivo.setBytes(Optional.ofNullable(multipartItem.getValues()).map(m -> (byte[]) m.get(0)).orElse(null));
                arquivos.add(arquivo);
            }
        }
        return arquivos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArquivoUpload that = (ArquivoUpload) o;

        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        return Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        int result = nome != null ? nome.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "ArquivoUpload{" +
                "nome='" + nome + '\'' +
                ", size=" + size +
                ", temPath='" + tempPath + '\'' +
                '}';
    }

}

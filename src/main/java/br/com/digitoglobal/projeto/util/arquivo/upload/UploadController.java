package br.com.digitoglobal.projeto.util.arquivo.upload;

import java.util.List;

import br.com.digitoglobal.projeto.util.jsf.view.ViewController;

@ViewController
public class UploadController {

    private List<ArquivoUpload> arquivos;

    public UploadController() {}

    public void submit() {
        System.out.println(arquivos);
    }

    public List<ArquivoUpload> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<ArquivoUpload> arquivos) {
        this.arquivos = arquivos;
    }

}

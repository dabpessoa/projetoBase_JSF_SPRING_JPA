package br.com.digitoglobal.projeto.util.arquivo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.com.digitoglobal.projeto.bean.arquivo.ArquivoBasico;
import br.com.digitoglobal.projeto.bean.model.BaseEntity;
import br.com.digitoglobal.projeto.util.FileUtils;
import br.com.digitoglobal.projeto.util.HibernateUtils;
import br.com.digitoglobal.projeto.util.arquivo.sincronizacao.DefaultFileUploadNameHandler;
import br.com.digitoglobal.projeto.util.arquivo.sincronizacao.DefaultFileUploadPathHandler;
import br.com.digitoglobal.projeto.util.arquivo.sincronizacao.FileSyncronizationManager;
import br.com.digitoglobal.projeto.util.arquivo.sincronizacao.FileUploadPathHandler;
import br.com.digitoglobal.projeto.util.optionalList.OptionalList;

public class ArquivoUploadHelper {

    public static void processarArquivos(BaseEntity entity, List<? extends ArquivoBasico> arquivos, Object genericService, String caminho) {
        FileUploadPathHandler fileUploadPathHandler = new DefaultFileUploadPathHandler(caminho, entity.getId());
        processarArquivos(entity, arquivos, genericService, fileUploadPathHandler);
    }

    public static void processarArquivos(BaseEntity entity, List<? extends ArquivoBasico> arquivos, Object genericService, FileUploadPathHandler fileUploadPathHandler) {

        // Processar arquivos.
        if (HibernateUtils.isInitialized(arquivos) && arquivos != null) {

            OptionalList<ArquivoBasico> arquivosOptionalList;
            if (arquivos instanceof OptionalList) {
                arquivosOptionalList = (OptionalList<ArquivoBasico>) arquivos;
            } else {
                arquivosOptionalList = new OptionalList<>((Collection<ArquivoBasico>) arquivos);
            }

            File[] files = new File(fileUploadPathHandler.gerarCaminhoBase()).listFiles();
            List<File> filesList = files != null ? Arrays.asList(files) : Collections.EMPTY_LIST;

            // Sincronizar arquivos do banco de dados com arquivos salvos no disco.
            // Registra ação para ser executada após o commit.
            // Isso garante que não fique sujeira nas pastas do disco rígido.
            FileSyncronizationManager.createAndRegisterSyncronization(filesList, arquivosOptionalList);

            for (ArquivoBasico arquivo : arquivosOptionalList) {
                // Se o id for nulo e tiver sido adicionado agora,
                // então salvar no disco rígido
                if (arquivo.isInserting() && arquivo.isTemporarilyAdded()) {
                    try {
                        arquivo.setNomeFisico(new DefaultFileUploadNameHandler(arquivo, entity));
                        fileUploadPathHandler.setNomeArquivo(arquivo.getNomeFisico());
                        arquivo.setCaminho(fileUploadPathHandler);

                        arquivo.escreverBytesNoDisco();
                        arquivo.setBytes(null); // Não salvar bytes no banco de dados, somente escrever o arquivo em disco.
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Erro ao salvar arquivo no disco. Mensagem: "+e.getMessage());
                    }
                } else {
                    // Se for um arquivo já salvo e foi removido da tela
                    // Então apagar do disco rígido.
                    if (arquivo.isUpdating() && arquivo.isTemporarilyRemoved()) {
                        arquivo.apagarDoDisco();
                    }
                }
            }

            List<ArquivoBasico> arquivosSalvar = arquivosOptionalList.getAllTemporarilyAdded();
            List<ArquivoBasico> arquivosExcluir = arquivosOptionalList.getAllTemporarilyRemoved();

//            // Salvar no banco de dados
//            genericService.insertOrUpdateAll(arquivosSalvar);
//            // Tornando os arquivos da lista que foram salvos no banco, itens permanentes na lista.
//            ((OptionalList<ArquivoBasico>) arquivosSalvar).setAllTemporarilyAdded(false);
//
//            // Excluir do banco de dados
//            genericService.deleteAllFast(arquivosExcluir);
//            // Removendo da lista os arquivos excluídos.
//            arquivosOptionalList.removeAll(arquivosExcluir);

        }

    }

    public static void removerTodosOsArquivosAPartirDaEntidadePai(BaseEntity entity, ServiceArquivavel serviceArquivavel, FileUploadPathHandler fileUploadPathHandler) {
        removerArquivosFisicosAPartirDaEntidadePai(fileUploadPathHandler);
//        removerArquivosDoBancoAPartirDaEntidadePai(entity, serviceArquivavel);
    }

    public static void removerArquivosFisicosAPartirDaEntidadePai(FileUploadPathHandler fileUploadPathHandler) {
        // Apagando arquivos físicos
        FileUtils.deleteQuietly(fileUploadPathHandler.gerarCaminhoBase());
    }

//    public static void removerArquivosDoBancoAPartirDaEntidadePai(BaseEntity entity, ServiceArquivavel serviceArquivavel) {
//        // Apagando arquivos do banco
//        serviceArquivavel.removerArquivosPorEntidadePai(entity);
//    }

}

package br.com.digitoglobal.projeto.util.arquivo.sincronizacao;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import br.com.digitoglobal.projeto.bean.arquivo.ArquivoBasico;

public class FileSyncronizationManager {

    public static void createAndRegisterSyncronization(final List<File> arquivosNoDisco, final List<? extends ArquivoBasico> arquivosNoBanco){
        final TransactionSynchronization syncronization = new TransactionSynchronization() {
            @Override
            public void afterCompletion(int arg0) {
                // Remover arquivos órfãos do disco rígido

                // Recupera lista de arquivos que estão no disco rígido mas não estão no banco de dados
                List<File> arquivosApagar = arquivosNoDisco.stream()
                                                .filter(ad -> arquivosNoBanco.stream()
                                                    .noneMatch(ab -> ab.getNomeFisico().equalsIgnoreCase(ad.getName())))
                                                .collect(Collectors.toList());

                // Apagar arquivos
                if (arquivosApagar != null) {
                    for (File arquivoApagar : arquivosApagar) {
                        FileUtils.deleteQuietly(arquivoApagar);
                    }
                }
            }

            @Override
            public void suspend() {}
            @Override
            public void resume() {}
            @Override
            public void flush() {}
            @Override
            public void beforeCompletion() {}
            @Override
            public void beforeCommit(boolean arg0) {}
            @Override
            public void afterCommit() {}
        };

        TransactionSynchronizationManager.registerSynchronization(syncronization);
    }

}

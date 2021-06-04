package server;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.services.*;

import java.util.concurrent.CompletableFuture;

public class DivekitLanguageServer implements LanguageServer, LanguageClientAware {
    private TextDocumentService textDocumentService;
    private WorkspaceService workspaceService;
    private LanguageClient client;
    private int errorCode = 1;

    public DivekitLanguageServer(String pathToVariablesConfig, String pathToExtensionsConfig) {
        this.textDocumentService = new DivekitTextDocumentService(pathToVariablesConfig, pathToExtensionsConfig);
        this.workspaceService = new DivekitWorkspaceService();
    }

    public CompletableFuture<InitializeResult> initialize(InitializeParams initializeParams) {

        final InitializeResult initializeResult = new InitializeResult(new ServerCapabilities());

        ServerInfo info = new ServerInfo();
        info.setName("Divekit Language Server");
        info.setVersion("1.0");

        initializeResult.setServerInfo(info);

        //TODO - check other sync kinds then full
        initializeResult.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Full);
        CompletionOptions completionOptions = new CompletionOptions();
        initializeResult.getCapabilities().setCompletionProvider(completionOptions);

        initializeResult.getCapabilities().setHoverProvider(true);

        return CompletableFuture.supplyAsync(() -> initializeResult);
    }

    public CompletableFuture<Object> shutdown() {
        errorCode = 0;

        return CompletableFuture.supplyAsync(() -> null);
    }

    public void exit() {
        System.exit(errorCode);
    }

    public TextDocumentService getTextDocumentService() {
        return this.textDocumentService;
    }

    public WorkspaceService getWorkspaceService() {
        return this.workspaceService;
    }

    @Override
    public void connect(LanguageClient languageClient) {
        this.client = languageClient;
    }

}

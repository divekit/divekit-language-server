package server;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import server.gen.ObjectVariableGenerator;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class DivekitTextDocumentService implements TextDocumentService {

    private List<CompletionItem> providedItems;
    private String pathToVariationsConfig;
    private String pathToExtensionsConfig;

    public DivekitTextDocumentService(String pathToVariationsConfig, String pathToExtensionsConfig) {
        this.pathToVariationsConfig = pathToVariationsConfig;
        this.pathToExtensionsConfig = pathToExtensionsConfig;

        Timer timer = new Timer();
        timer.schedule(new ReloadCompletionItemsTask(), 0, 10000);

        loadCompletionItems();
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams completionParams) {
        return CompletableFuture.supplyAsync(() -> {
            List<CompletionItem> completionItems;

            completionItems = this.providedItems;

            // Return the list of completion items.
            return Either.forLeft(completionItems);
        });
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams didOpenTextDocumentParams) {

    }

    @Override
    public void didChange(DidChangeTextDocumentParams didChangeTextDocumentParams) {

    }

    @Override
    public void didClose(DidCloseTextDocumentParams didCloseTextDocumentParams) {

    }

    @Override
    public void didSave(DidSaveTextDocumentParams didSaveTextDocumentParams) {

    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        return CompletableFuture.supplyAsync(() -> {
            Hover hover = new Hover();
            MarkupContent content = new MarkupContent();
            content.setKind("Completion Item");
            content.setValue("A completion item provided by the Divekit Language Server");

            hover.setContents(content);

            return hover;
        });
    }

    private void loadCompletionItems() {
        providedItems = new ArrayList<>();

        ObjectVariableGenerator objectVariableGenerator = new ObjectVariableGenerator(pathToVariationsConfig, pathToExtensionsConfig);
        ArrayList<String> variables = objectVariableGenerator.readVariables();

        for(String variable : variables) {

            String itemTxt = "$" + variable + "$";

            CompletionItem completionItem = new CompletionItem();
            completionItem.setInsertText(itemTxt);
            completionItem.setLabel(itemTxt);
            completionItem.setKind(CompletionItemKind.Snippet);
            completionItem.setDetail("CompletionItem");

            providedItems.add(completionItem);
        }
    }

    class ReloadCompletionItemsTask extends TimerTask {
        @Override
        public void run() {
            loadCompletionItems();
        }
    }
}

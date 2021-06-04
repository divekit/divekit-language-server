package launcher;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import server.DivekitLanguageServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class StdioLauncher {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //prevent interrupts
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(Level.OFF);

        String pathToVariationsConfig = args[0];
        String pathToExtensionsConfig = args[1];

        //call method which starts the language server
        startServer(pathToVariationsConfig, pathToExtensionsConfig);
    }

    private static void startServer(String pathToVariationsConfig,
                                    String pathToExtensionsConfig) throws ExecutionException, InterruptedException {
        DivekitLanguageServer server = new DivekitLanguageServer(pathToVariationsConfig, pathToExtensionsConfig);

        Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, System.in, System.out);

        LanguageClient client = launcher.getRemoteProxy();

        server.connect(client);

        Future<?> startListening = launcher.startListening();

        startListening.get();
    }
}

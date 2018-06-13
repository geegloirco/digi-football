package ir.geeglo.business;

import ir.piana.dev.webtool2.server.PianaAnnotationAppMain;
import ir.piana.dev.webtool2.server.annotation.PianaServer;
import ir.piana.dev.webtool2.server.annotation.PianaServerCORS;
import ir.piana.dev.webtool2.server.annotation.PianaWebSocket;

//@PianaServer(httpIp = "185.105.239.254", httpPort = 80, httpDocIp = "185.105.239.254", httpDocPort = 80)
//@PianaWebSocket(socketIp = "185.105.239.254", socketPort = 8008)
@PianaServer(httpIp = "piana.ir", httpPort = 9000,
        httpDocIp = "piana.ir", httpDocPort = 9001,
        serverCORS = @PianaServerCORS(allowOrigin = "http://piana.ir"))
@PianaWebSocket(socketIp = "piana.ir", socketPort = 8008)
public class DigiFootballAppMain {
    public static void main(String[] args) throws Exception {
        PianaAnnotationAppMain.start(DigiFootballAppMain.class);

//        Options o = new Options();
//
//        YUICompressor yuiCompressor = new YUICompressor();
//
//        try {
//            Reader reader = new InputStreamReader(PianaFootballAppMain.class
//                    .getResourceAsStream("/one.component.js"));
//            JavaScriptCompressor compressor = new JavaScriptCompressor(
//                    reader, new YuiCompressorErrorReporter());
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//            OutputStreamWriter writer = new OutputStreamWriter(
//                    new FileOutputStream("d:/comp.min.js"), o.charset);
//            compressor.compress(writer,
//                    o.lineBreakPos,
//                    o.munge,
//                    o.verbose,
//                    o.preserveAllSemiColons,
//                    o.disableOptimizations);
//        } finally {
//        }
//    }

//    private static Logger logger = Logger.getLogger(YUICompressor.class.getName());
//
//    private static class YuiCompressorErrorReporter implements ErrorReporter {
//        public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
//            if (line < 0) {
//                logger.log(Level.WARNING, message);
//            } else {
//                logger.log(Level.WARNING, line + ':' + lineOffset + ':' + message);
//            }
//        }
//
//        public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
//            if (line < 0) {
//                logger.log(Level.SEVERE, message);
//            } else {
//                logger.log(Level.SEVERE, line + ':' + lineOffset + ':' + message);
//            }
//        }
//
//        public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
//            error(message, sourceName, line, lineSource, lineOffset);
//            return new EvaluatorException(message);
//        }
//    }
//
//    public static class Options {
//        public String charset = "UTF-8";
//        public int lineBreakPos = -1;
//        public boolean munge = true;
//        public boolean verbose = false;
//        public boolean preserveAllSemiColons = false;
//        public boolean disableOptimizations = false;
    }
}

//PACOTE
package edu.ufsj.lox;

//Imports ^-^
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;


@SuppressWarnings("unused")
public class Lox {
    static boolean hadError = false; //Setando o has error inicialmente :O
    public static void main(String[] args) throws IOException{
        if (args.length > 1){ //Se cair aqui ta erradão
            System.err.println("Usage jlox [script]");
            System.exit(64);
        } else if (args.length == 1) { //Se tiver tamanho igual a um, tentar rodar o arquivo
            runFile(args[0]); 
        } else { //Caso contrário roda a prompt 
            runPrompt(); 
        }
        
    }

    //All the other kid with the pumped up python better run, better run, faster than my code!
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        for (Token token : tokens){
            System.out.println(token);
        }
    }

    //Abre o prompt para escrever
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        for(;;){
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run (line);
            hadError = false; //Por que resetar a cada iteração? 
        }
    }

    //recebe um arquivo e converte em binario
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(65); //Se deu biziu, sair
        
    }
    
    static void error (int line, String message){ //Mensagem de erro :D
        report(line, "", message);
    }

    private static void report (int line, String where, String message){ //Estrutura da mensagem :D
        System.out.println("[line " + line + "] Error :" + where + ":"+ message);
        hadError = true;
    }
}
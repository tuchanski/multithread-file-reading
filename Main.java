import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        initializeAlphabet();

        File files[];

        // Caso queira trocar o diretório, alterar o pathname nessa variável aí
        File dir = new File("./todosArquivos");
        files = dir.listFiles();

        Long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (File file : files){
            executorService.submit(() -> countChar(file));
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        
        Long end = System.currentTimeMillis();
        Long totalTime = end - start;

        getElements();

        System.out.println("Total time: " + totalTime + "ms");
    }

    // Dicionário com letra e contador
    private static HashMap<Character, Integer> alphabet = new HashMap<>();

    public static void initializeAlphabet() {
        for (char letter = 'a'; letter <= 'z'; letter++) {
            alphabet.put(letter, 0);
        }
    }

    public static void getElements() {
        for (char letter : alphabet.keySet()) {
            System.out.println(letter + ": " + alphabet.get(letter));
        }
    }

    // Contagem de caracteres com threads
    public static void countChar(File file) {

        try {

            FileReader fileReader = new FileReader(file.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            try {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    char lineArr[] = line.toCharArray();

                    // Sincroniza o acesso ao dicionário (pois muitas threads vão tentar alterar a chave ao mesmo tempo)
                    synchronized (alphabet) {
                        for (char letter : lineArr) {
                            char lowerCaseLetter = Character.toLowerCase(letter);
                            if (alphabet.containsKey(lowerCaseLetter)) {
                                alphabet.put(lowerCaseLetter, alphabet.get(lowerCaseLetter) + 1);
                            }
                        }
                    }
                }

                bufferedReader.close();
                fileReader.close();

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}

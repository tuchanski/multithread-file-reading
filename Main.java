import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main{

    public static void main(String[] args) throws InterruptedException {

        initializeAlphabet();

        File files[];

        // Caso queira trocar o diretório, alterar o pathname nessa variável aí
        File dir = new File("./todosArquivos");
        files = dir.listFiles();

        List<Thread> threads = new ArrayList<>();

        Long start = System.currentTimeMillis();

        for (int i = 0; i < files.length; i++) {
            Thread thread = countChar(files[i]);
            if (thread != null) {
                thread.start();
                threads.add(thread);
            }
        }

        for (Thread t : threads) {
            t.join(); // Aguarda a finalização de todas as threads
        }

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
    public static Thread countChar(File file) {

        try {

            FileReader fileReader = new FileReader(file.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            Thread t = new Thread() {

                @Override
                public void run() {
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
                }
            };

            return t;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

}

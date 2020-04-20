package tibiamining;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import regrasdenegocio.CheckCharacter;

public class TestesComArquivos {

    public static void main(String[] args) {
        List<String> listaItens = new ArrayList<>();

        try {

            /* Alterar  */
            String file = "C:\\Users\\fiodo\\OneDrive\\Área de Trabalho\\tmteste.txt";
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] itens = null;

            while (br.ready()) {

                itens = br.readLine().split("&");
                listaItens.add(itens[0]);

            }
            
            new CheckCharacter().updateCharacter("Crimsix Tanagaiola", listaItens);

        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado: " + ex);
        } catch (IOException ex) {
            System.out.println("Falha I/O - E/S: " + ex);
        }
    }
}

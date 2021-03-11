package secao17exerc01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class App {

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Product> list = new ArrayList<>();
		
		System.out.println("Enter a file path: ");
		
		// Armazena em um string o caminho COMPLETO que ser� digitado
		String sourceFileStr = sc.nextLine(); 
		
		//Cria uma variavel "File" para referenciar o caminho digitado
		File sourceFile = new File(sourceFileStr);
		
		//Armazena em outra String SOMENTE O CAMINHO da pasta do arquivo
		String sourceFolderStr = sourceFile.getParent();
		
		//Cria um "File" e passa como argumento a String s� com o caminho de pasta concatenado com 
		//o que deve ser a nova pasta - usa o m�todo "mkdir" para criar essa nova pasta do argumento 
		//concatenado desse novo "File". O boolean success � usado somente para fazer esse procedimento.
		boolean success = new File(sourceFolderStr + "\\out").mkdir();
		
		//Cria mais uma String com o caminho final do arquivo e o novo do novo arquivo - concatenando 
		//o caminho j� separado pelo ".getParent" com o novo diret�rio criado (\\"out") mais o nome
		//do novo arquivo ("summary.csv")
		String targetFileStr = sourceFolderStr + "\\out\\summary.csv";
		
		
		//Cria um "try" com resources, instancia um BufferedReader que recebe um new FileReader referenciando
		//a mesma String de caminho digitada pelo usu�rio. 
		// TRY para ler o arquivo e criar objetos de cada um
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))){
					
			String itemCsv = br.readLine(); // L� a primeira linha do arquivo e coloca numa String como um item
											// do arquivo. As pr�ximas linhas s�o lidas dentro do while
			
			// Cria um "while" para ler cada Linha do arquivo, enquanto a linha lida for diferente de "null"
			while(itemCsv != null) {
				
				String[] fields = itemCsv.split(","); // Cria um vetor de Strings e separa cada item por ","
				
				String name = fields[0];
				double price = Double.parseDouble(fields[1]); 
				int quantity = Integer.parseInt(fields[2]);
				
				list.add(new Product(name,price,quantity)); // cria um produto e adiciona na lista
				
				itemCsv = br.readLine(); // L� mais uma linha do arquivo, se for "null", o programa encerra
				
				
		     }
			 // TRY para criar e escrever os objetos copiados do arquvio lido em um novo arquivo CSV
			// Quando "FileWriter" � instanciado com a String "targetFileStr", � criado o arquivo summary.csv
			 try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {
				 // FOR que escreve em cada linha os dados do objeto, Nome e o c�lculo do total de cada item
				 for(Product item : list) {
					 bw.write(item.getName() + "," + String.format("%.2f", item.total()));
					 bw.newLine();
				 }
				 
				 System.out.println(targetFileStr + " CREATED");
			 } catch(IOException e) {
				 System.out.println("Error writing file: " + e.getMessage());
			 }
			 
		} catch(IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		
		sc.close();
		
		
	}

}

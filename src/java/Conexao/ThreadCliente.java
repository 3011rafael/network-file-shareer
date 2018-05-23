/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

import Host.Host;
import Host.Peer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;


/**
 *
 * @author Rafael Macedo
 */
public class ThreadCliente extends Thread{

    
    private Socket cliente;
    private ObjectInputStream in;
    private ObjectOutputStream out; 
    private Peer peer;
    private String msg;
    private String textoGetHosts = "";
    private String textoSearchFile = "";
    
    public ThreadCliente(Socket cliente) throws IOException{
        this.cliente = cliente;
        
            try {
                out = new ObjectOutputStream(cliente.getOutputStream());
                in = new ObjectInputStream(cliente.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

        System.out.println("Passou");
        peer = new Peer();
        
        File getHosts = new File("C:\\Users\\Rafael Macedo\\Desktop\\2015.1\\Programação para Redes\\Projeto_FileSharing\\Projeto_FileSharing\\Arquivos XML\\getHosts.xml");
        Scanner scanner; // FileReader para ler o arquivo de texto.
        scanner = new Scanner(new FileReader(getHosts));
      
        
        while (scanner.hasNextLine()) {
            String x = scanner.nextLine();
            textoGetHosts = textoGetHosts + x + "\n";
        } 
        
        File searchFile = new File("C:\\Users\\Rafael Macedo\\Desktop\\2015.1\\Programação para Redes\\Projeto_FileSharing\\Projeto_FileSharing\\Arquivos XML\\searchFile.xml");
        Scanner scanner2; // FileReader para ler o arquivo de texto.
        scanner2 = new Scanner(new FileReader(searchFile));
      
        
        while (scanner2.hasNextLine()) {
            String x = scanner2.nextLine();
            textoSearchFile = textoSearchFile + x + "\n";
        }  
        
    }
    
    public void run(){
        
        System.out.println("Thread Funcionando: Aguardando Mensagem");
        
            try {
                while (((msg = (String) in.readObject()) != "fim")){// Enquanto a conexÃ£o nÃ£o estiver fechada     
                    System.out.println("Thread Funcionando: Aguardando opcao");
                    System.out.println("msg: " + msg);
                    //msg = (String) in.readObject(); // faz a leitura da msg recebida     
                    if (msg.equals(this.textoGetHosts)) {
                        System.out.println("entrou no if");
                        //String textoGetHosts = (String) in.readObject();
//                        peer.addHost(new Host("123.0.1.1", 1233));
//                        peer.addHost(new Host("192.4.1.1", 2233));
                       
//                        ArrayList hosts = peer.getListaHosts();
                        
                        File getHostResponse = new File("E:\\Projeto_FileSharing\\Arquivos XML\\getHostResponse.xml");
                        String textoGetHostsResponse = "";
                        Scanner scanner; // FileReader para ler o arquivo de texto.
                        scanner = new Scanner(new FileReader(getHostResponse));

                        while (scanner.hasNextLine()) {
                            String x = scanner.nextLine();
                            textoGetHostsResponse = textoGetHostsResponse + x + "\n";
                        }  
                        
//                        String textoFinal = textoGetHostsResponse.split("<getHostsResponse>")[0] + "<getHostsResponse>" + "\n";
//                        String parte2 = "</getHostsResponse>" + textoGetHostsResponse.split("</getHostsResponse> ")[1]; //pegando a segunda parte do xml
//                        int i = 0;
//                        for (i = 0; i < hosts.size(); i++) {
//                            textoFinal = textoFinal + "<host>" + "\n" + "<ip>" + String.valueOf(((Host) hosts.get(i)).getIp()) + "</ip>" + "\n"
//                                    + "<port>" + String.valueOf(((Host) hosts.get(i)).getPorta()) + "</port>" + "\n" + "</host>" + "\n";
//
//                        }
//                        textoFinal = textoFinal + parte2;
//                        System.out.println(textoFinal);
                        out.writeObject(textoGetHostsResponse);
                        System.out.println("Enviou o getHostResponse");

                    } 
                    else if(msg.contains("<searchFile>")){
                        System.out.print("entrou");
                        File dir = new File("C:\\Users\\LabRedes-01\\Downloads\\Projeto_FileSharing\\Projeto_FileSharing\\src\\java\\Files");
                        File[] arquivosLocais = dir.listFiles();
                        ArrayList arquivosEncontrados= new ArrayList();
                        
                        String textoSearchFiles = msg;
                        
                        //Separando palavras chaves
                        String[] linhas = textoSearchFiles.split("\n");
                        for(int i=0; i<linhas.length; i++){
                            if(linhas[i].contains("<keywords>")){
                                String part1 = linhas[i].split("<keywords>")[1];
                                String palavra = part1.split("</keywords>")[0];
                                for(int j=0; j<arquivosLocais.length; j++){
                                    if(arquivosLocais[j].getName().contains(palavra)){
                                        arquivosEncontrados.add(arquivosLocais[j]);
                                    }
                                }
                                
                            }
                        }
                        
                        File searchFileResponse = new File("C:\\Users\\LabRedes-01\\Downloads\\Projeto_FileSharing\\Projeto_FileSharing\\Arquivos XML\\searchFileResponse.xml");
                        String textoSearchFileResponse = "";
                        Scanner scanner; // FileReader para ler o arquivo de texto.
                        scanner = new Scanner(new FileReader(searchFileResponse));

                        while (scanner.hasNextLine()) {
                            String x = scanner.nextLine();
                            textoSearchFileResponse = textoSearchFileResponse + x + "\n";
                        } 
                        
                        String textoFinal = textoSearchFileResponse.split("<searchFileResponse>")[0] + "<searchFileResponse>" + "\n";
                        String parte2 = "</searchFileResponse>" + textoSearchFileResponse.split("</searchFileResponse>")[1]; //pegando a segunda parte do xml
                        int i = 0;
                        
                        for(i=0; i<arquivosEncontrados.size(); i++){
                            textoFinal = textoFinal + "<file>" + "\n" + "<fileName>" + ((File)arquivosEncontrados.get(i)).getName() + "</fileName>" + "\n"
                                    + "<fileSize>" + String.valueOf(((File)arquivosEncontrados.get(i)).length()) + "</fileSize>" + "\n" + "</file> " + "\n";
                        }
                        
                        textoFinal = textoFinal + parte2;
                        System.out.println(textoFinal);
                        out.writeObject(textoFinal);
                        
                        
                    }
                    
                    else if(msg.contains("<getFiles>")){
                        String textoGetFiles = msg;
                        String aux1 = textoGetFiles.split("<getFiles>")[1]; //pegando a primeira parte do xml
                        
                        String aux2 = aux1.split("</getFiles>")[0]; //pegando a segunda parte do xml
                        String[] linhas = aux2.split("\n");
                        System.out.print("aux 1 " + aux1);
                        
                        
                        //Pegando texto do getFileResponse
                        File getFileResponse = new File("C:\\Users\\Rafael Macedo\\Desktop\\2015.1\\Programação para Redes\\Projeto_FileSharing\\Projeto_FileSharing\\Arquivos XML\\getFileResponse.xml");
                        String textoGetFileResponse = "";
                        Scanner scanner; // FileReader para ler o arquivo de texto.
                        scanner = new Scanner(new FileReader(getFileResponse));

                        while (scanner.hasNextLine()) {
                            String x = scanner.nextLine();
                            textoGetFileResponse = textoGetFileResponse + x + "\n";
                        }
                        
                        String parte1 = textoGetFileResponse.split("<getFilesResponse>")[0] + "<getFilesResponse>" + "\n";
                        String parte2 = "</getFilesResponse>" + textoGetFileResponse.split("</getFilesResponse>")[1]; 
                        
                        textoGetFileResponse = parte1;
                        int i = 0;
                        System.out.print("linha " + linhas[0]); 
                        for (i = 0; i < linhas.length; i++) {
                            System.out.println("Linha: "+ linhas[i]);     
                            if(!linhas[i].equals("")){
                            String aux = linhas[i].split("<fileName>")[1];
                            String arquivo = aux.split("</fileName>")[0];
                            System.out.print("nome arq " +arquivo);
                            File file = new File("C:\\Users\\Rafael Macedo\\Desktop\\2015.1\\Programação para Redes\\Projeto_FileSharing\\Projeto_FileSharing\\src\\java\\Files\\"+ arquivo);
                                                                                                            
                            byte[] bytes = loadFile(file);
                            //byte[] encoded = Base64.encode(bytes);
                            String encodedString = Base64.encode(bytes);
                            
                            textoGetFileResponse = textoGetFileResponse + "<fileData>" + "\n" + 
                                    "<fileName>" +  arquivo + "</fileName>" + "\n"
                                    + "<data encode=\"\">" + encodedString +  "</data>" + "\n";
                            }
   
                        }
                        String textoFinal = textoGetFileResponse + parte2;
                        out.writeObject(textoFinal);
                        System.out.print(textoFinal);

                                
                    }
                    
                   
                    System.out.println("novo laço");
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        
        
    }

    private void controlador(String msg) throws IOException, ClassNotFoundException {
         
         
    }

    private byte[] loadFile(File file) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(file);
 
	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }
 
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }
 
	    is.close();
	    return bytes;
    }

        

    
}

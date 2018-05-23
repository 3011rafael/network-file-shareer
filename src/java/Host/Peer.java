/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Host;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.Scanner;

/**
 *
 * @author Rafael Macedo
 */
public class Peer {
    
    private static String[] arquivosLocais;
    private ArrayList listaHosts;
    
    public Peer(){
        listaHosts = new ArrayList();
    }
    
    public void listarArquivosLocais(){
        File dir = new File("Files");
        arquivosLocais = dir.list();
    }
    
    public ArrayList buscarArquivoLocal(String palavraChave){
        int i=0, j=0;
        ArrayList arquivosEncontrados = new ArrayList();
        listarArquivosLocais();       
        for(i=0; i<arquivosLocais.length ;i++){
            if(arquivosLocais[i].contains(palavraChave)){
                arquivosEncontrados.add(arquivosLocais[i]);
                j++;
            }
        }
        return arquivosEncontrados;
    }
    
     //Esse Ã© a user Story 2 -> Listar Hosts Remotos
    public String getHosts() throws FileNotFoundException{
        File getHosts = new File("getHosts.xml");
        String textoGetHosts = "";
        Scanner scanner; // FileReader para ler o arquivo de texto.
        scanner = new Scanner(new FileReader(getHosts));
      
        
        while (scanner.hasNextLine()) {
            String x = scanner.nextLine();
            textoGetHosts = textoGetHosts + x + "\n";
        }  
        return textoGetHosts;
        //precisa fazer o cÃ³digo de enviar essa string para o host do IP do parÃ¢metro
    }
    
    //Esse Ã© a user Story 2 -> Listar Hosts Remotos
    public String getHostResponse() throws FileNotFoundException{
        File getHostResponse = new File("getHostResponse.xml");
        String textoGetHostResponse = "";
        Scanner scanner; // FileReader para ler o arquivo de texto.
        scanner = new Scanner(new FileReader(getHostResponse));
      
        while (scanner.hasNextLine()) {
            String x = scanner.nextLine();
            textoGetHostResponse = textoGetHostResponse + x + "\n";
        }  
        /*
        //Separando o arquivo XML em partes
        String parte1 = textoGetHostResponse.split("<getHostsResponse>")[0] + "<getHostsResponse>"+ "\n" ; //pegando a primeira parte do xml
        String parte2 = "</getHostsResponse>" + textoGetHostResponse.split("</getHostsResponse> ")[1]; //pegando a segunda parte do xml
        
        //Adicionar a lista de hosts no XML
        String arquivoFinal = parte1;
        int i;
        for(i=0;i<listaHosts.size();i++){
            Host atual = (Host) listaHosts.get(i);
            arquivoFinal = arquivoFinal + "  <host> " + "\n"
                    + " <ip> " + atual.getIp() + " </ip> " + "\n"
                    + " <port> " + atual.getPorta() + " </port> " + "\n" + "</host>" + "\n"; 
        }
        arquivoFinal = arquivoFinal + parte2;*/
       // System.out.print(textoGetHostResponse);
        return textoGetHostResponse;
        
        
    }
    
    public void getFile(ArrayList arquivos) throws FileNotFoundException{
        File getFile = new File("getFiles.xml");
        String textoGetFile = "";
        Scanner scanner; // FileReader para ler o arquivo de texto.
        scanner = new Scanner(new FileReader(getFile));
        int i=0, j=0;
        while (scanner.hasNextLine()) {
            i=0; 
            j=0;
            String x = scanner.nextLine();
            textoGetFile = textoGetFile + x + "\n";
        }  
        scanner.close();
        //Separando o arquivo XML em partes
        String parte1 = textoGetFile.split("<getFiles>")[0] + "<getFiles>"+ "\n" ; //pegando a primeira parte do xml
        String parte2 = "</getFiles>" + textoGetFile.split("</getFiles>")[1]; //pegando a segunda parte do xml
        
        String arquivoFinal = parte1;
        for(i=0;i<arquivos.size();i++){
            arquivoFinal = arquivoFinal + " <fileName> " + arquivos.get(i) + " </fileName> " + "\n"; 
        }
        arquivoFinal = arquivoFinal + parte2;
        
        System.out.print(arquivoFinal);
        //aqui precisamos enviar o arquivoFinal para o outro host

    }
    
    public void getFileResponse(ArrayList arquivos) throws FileNotFoundException{
        File getFileResponse = new File("getFileResponse.xml");
        String textoGetFileResponse = "";
        Scanner scanner; // FileReader para lÃƒÂª o arquivo de texto.
        scanner = new Scanner(new FileReader(getFileResponse));
        
        int i=0, j=0;
        while (scanner.hasNextLine()) {
            i=0; 
            j=0;
            String x = scanner.nextLine();
            textoGetFileResponse = textoGetFileResponse + x + "\n";
        }  
        scanner.close();
        
        //Separando o arquivo XML em partes
        String parte1 = textoGetFileResponse.split("<getFilesResponse>")[0] + "<getFilesResponse>"+ "\n" ; //pegando a primeira parte do xml
        String parte2 = "</getFilesResponse> " + textoGetFileResponse.split("</getFilesResponse> ")[1]; //pegando a segunda parte do xml
        
        String arquivoFinal = parte1;
        for(i=0;i<arquivos.size();i++){
        //dentro deste for precisamos fazer um mÃ©todo para a coversÃ£o do texto do arquivo para encode
            arquivoFinal = arquivoFinal + (" <fileData> " + "\n" + "<fileName> "  
                    + arquivos.get(i) + " </fileName> " + "\n"
                    + " <data encode=\"alguma\"> " + arquivos.get(i) + " </data> " + "\n"
                    + " </fileData> " + "\n"); 
        }
        
        arquivoFinal = arquivoFinal + parte2;
        System.out.print(arquivoFinal);
    }

    public String[] getArquivosLocais() {
        return arquivosLocais;
    }

    public void setArquivosLocais(String[] arquivosLocais) {
        Peer.arquivosLocais = arquivosLocais;
    }

    public ArrayList getListaHosts() {
        return listaHosts;
    }

    public void setListaHosts(ArrayList listaHosts) {
        this.listaHosts = listaHosts;
    }
    
    public void addHost(Host host){
        this.listaHosts.add(host);
    }
    
    
    
    
    
    public static void main(String[] args) throws FileNotFoundException{
        Peer p = new Peer();
        p.listarArquivosLocais();
        int i=0;
        System.out.println("Todos os arquivos");
        for(i=0; i<arquivosLocais.length ;i++){
            System.out.println(arquivosLocais[i]);
        }
        System.out.println("-------------------");
        ArrayList busca = p.buscarArquivoLocal("A");
        if(busca.size()==0){
            System.out.println("Nenhum arquivo encontrado!");
        }
        else{
            for(i=0; i<busca.size() ;i++){
                System.out.println(busca.get(i));
            }
        }
        ArrayList teste = new ArrayList();
        teste.add("Rafael.txt");
        teste.add("Outro Arquivo.txt");
        /* p.getFile(teste);
        
        Host novo = new Host("127.0.0.1", 1020);
        p.listaHosts.add(novo);
        p.getHostResponse(null, 0);
        */
        String clearText = "Hello world";
        String encodedText;
        byte[] c = clearText.getBytes();
        // Base64
//        encodedText = new String(Base64.getEncoder().encode(c));
  //      System.out.print(encodedText);
        
        
    }
    
    
    
}

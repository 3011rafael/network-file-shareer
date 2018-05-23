/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Conexao.Cliente;
import Host.Arquivo;
import Host.Host;
import Host.Peer;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Rafael Macedo
 */
public class Controller {

    private Peer peer;
    public String palavra;

    public Controller() {
        peer = new Peer();
    }

//    public void rodarCliente(String ip, int porta) throws IOException{
//        Cliente c = new Cliente(ip, porta);
//    }
//    
//    public String[] listarArquivosLocais(){
//        peer.listarArquivosLocais();
//        return peer.getArquivosLocais();
//    }
//    
//    public String buscarArquivosLocais(String[] arquivos ){
//        for(int i =0; i<= listarArquivosLocais().length;i++){
//            
//            
//        }
//        return "";
//    }
    public ArrayList<Host> getHosts(String ipHost, int portaHost) throws IOException, ClassNotFoundException {
        Cliente c = new Cliente(ipHost, portaHost);
        ArrayList<Host> hosts = new ArrayList<Host>();

        //Leitura do getHosts
        File getHosts = new File("C:\\Users\\LabRedes-01\\Downloads\\Projeto_FileSharing\\Projeto_FileSharing\\Arquivos XML\\getHosts.xml");
        String textoGetHosts = "";
        Scanner scanner; // FileReader para ler o arquivo de texto.
        scanner = new Scanner(new FileReader(getHosts));

        while (scanner.hasNextLine()) {
            String x = scanner.nextLine();
            textoGetHosts = textoGetHosts + x + "\n";
        }
        c.getOut().writeObject(textoGetHosts);
        System.out.println("Enviou o GetHost");
        //System.out.println("Enviou a msg");
        //c.getOut().writeObject(peer.getHosts()); // Envia o XML getHosts para o servidor do IP de parâmetro

        String textoGetHostResponse = (String) c.getIn().readObject(); // Faz a leitura do XML getFileResponse enviado pelo servidor
        System.out.println("Recebeu o GetHostResponse");
        //String parte1 = textoGetHostResponse.split("<getHostsResponse>")[0] + "<getHostsResponse>"+ "\n";
        //String parte2 = "</getHostsResponse>" + textoGetHostResponse.split("</getHostsResponse> ")[1];

        String parte1 = textoGetHostResponse.split("<getHostsResponse>")[1] + "\n"; //pegando a primeira parte do xml
        String principal = parte1.split("</getHostsResponse>")[0]; //pegando a segunda parte do xml
        String[] linhas = principal.split("\n");
        int i = 0;

        for (i = 0; i < linhas.length; i++) {

            if (linhas[i].contains("<host>")) {
                String aux = linhas[i + 1].split("<ip>")[1];
                String ip = aux.split("</ip>")[0];
                String aux2 = linhas[i + 2].split("<port>")[1];
                String porta = aux2.split("</port>")[0];
                Host host = new Host(ip, Integer.parseInt(porta));
                hosts.add(host);

            }

        }
        return hosts;
    }

    public ArrayList<Arquivo> searchFile(String ip, int porta, ArrayList palavrasChave) throws IOException, ClassNotFoundException {
        ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
        Cliente c = new Cliente(ip, porta);

        File searchFile = new File("C:\\Users\\LabRedes-01\\Downloads\\Projeto_FileSharing\\Projeto_FileSharing\\Arquivos XML\\searchFile.xml");
        String textoSearchFile = "";
        Scanner scanner; // FileReader para ler o arquivo de texto.
        scanner = new Scanner(new FileReader(searchFile));

        while (scanner.hasNextLine()) {
            String x = scanner.nextLine();
            textoSearchFile = textoSearchFile + x + "\n";
        }

        String part1 = textoSearchFile.split("<searchFile>")[0] + "<searchFile>";
        String part2 = "</searchFile>" + textoSearchFile.split("</searchFile>")[1];

        textoSearchFile = part1 + "\n";

        for (int i = 0; i < palavrasChave.size(); i++) {
            textoSearchFile = textoSearchFile + "<keywords>" + ((String) palavrasChave.get(i)) + "</keywords>" + "\n";
        }
        textoSearchFile = textoSearchFile + part2;

        c.getOut().writeObject(textoSearchFile);

        String textoSearchFileResponse = (String) c.getIn().readObject();

        String parte1 = textoSearchFileResponse.split("<searchFileResponse>")[1] + "\n"; //pegando a primeira parte do xml
        String principal = parte1.split("</searchFileResponse> ")[0]; //pegando a segunda parte do xml
        String[] linhas = principal.split("\n");
        int i = 0;
        for (i = 0; i < linhas.length; i++) {
            if (linhas[i].equals("<file>")) {
                String aux = linhas[i + 1].split("<fileName>")[1];
                String nome = aux.split("</fileName>")[0];

                String aux2 = linhas[i + 2].split("<fileSize>")[1];
                String tamanho = aux2.split("</fileSize>")[0];
                Arquivo arquivo = new Arquivo(nome, tamanho);
                arquivos.add(arquivo);
            }

        }

        return arquivos;
    }

    public void getFiles(String ip, int porta, String[] nomesArquivos) throws IOException, ClassNotFoundException, Base64DecodingException {
        File[] arquivos = null;

        Cliente c = new Cliente(ip, porta);

        File getFiles = new File("C:\\Users\\Rafael Macedo\\Desktop\\2015.1\\Programação para Redes\\Projeto_FileSharing\\Projeto_FileSharing\\Arquivos XML\\getFiles.xml");
        String textoGetFiles = "";
        Scanner scanner; // FileReader para ler o arquivo de texto.
        scanner = new Scanner(new FileReader(getFiles));

        while (scanner.hasNextLine()) {
            String x = scanner.nextLine();
            textoGetFiles = textoGetFiles + x + "\n";
        }

        String aux1 = textoGetFiles.split("<getFiles>")[0] + "<getFiles>" + "\n"; //pegando a primeira parte do xml
        String aux2 = "</getFiles>" + textoGetFiles.split("</getFiles>")[1]; //pegando a segunda parte do xml

        textoGetFiles = aux1;

        int i = 0;
        for (i = 0; i < nomesArquivos.length; i++) {
            textoGetFiles = textoGetFiles + "" + "<fileName>" + nomesArquivos[i] + "</fileName>" + "\n";
        }
        textoGetFiles = textoGetFiles + aux2;

        c.getOut().writeObject(textoGetFiles);

        String textoGetFileResponse = (String) c.getIn().readObject();

        String parte1 = textoGetFileResponse.split("<getFilesResponse>")[1] + "\n"; //pegando a primeira parte do xml
        String principal = parte1.split("</getFilesResponse>")[0]; //pegando a segunda parte do xml
        String[] linhas = principal.split("\n");

        for (i = 0; i < linhas.length; i++) {
            if (linhas[i].contains("<fileData>")) {
                String aux = linhas[i + 1].split("<fileName>")[1];
                String nomeArq = aux.split("</fileName>")[0];
                System.out.println("NomeArq: " + nomeArq);
                String outroAux = linhas[i + 2].split("<data encode=\"\">")[1];
                System.out.println("OutroAux: " + outroAux);
                String encode = outroAux.split("</data>")[0];

                byte[] arquivo = Base64.decode(encode.getBytes());

                File file = new File("C:\\Users\\Rafael Macedo\\Desktop\\2015.1\\Programação para Redes\\\\Projeto_FileSharing\\Projeto_FileSharing\\src\\\\java\\FilesBaixados\\" + nomeArq); //Criamos um nome para o arquivo  
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file)); //Criamos o arquivo  
                bos.write(arquivo); //Gravamos os bytes lá  
                bos.close(); //Fechamos o stream.  
//                java.io.File file = new java.io.File("C:\\Users\\Rafael Macedo\\Desktop\\2015.1\\Programação para Redes\\Projeto_FileSharing\\Projeto_FileSharing\\src\\java\\Files Baixados\\" + nomeArq);
//                FileOutputStream in = new FileOutputStream(file);
//                in.write(arquivo);
//                in.close();
//                
//                File file2 = new File(nomeArq);
//                
//                FileWriter fw = new FileWriter(file2);
//                BufferedWriter bw = new BufferedWriter(fw);
//                
//                bw.write(new String(arquivo));
//                bw.flush();
//                bw.close();

                //aqui devo fazer o decode
            }

        }

    }

    public void atualizarHostsLocais(String ip, int porta) throws IOException, ClassNotFoundException {
        ArrayList<Host> listaHosts = this.getHosts(ip, porta);
        File getHostResponse = new File("E:\\Projeto_FileSharing\\Arquivos XML\\getHostResponse.xml");
        String textoGetHostsResponse = "";
        Scanner scanner; // FileReader para ler o arquivo de texto.
        scanner = new Scanner(new FileReader(getHostResponse));

        while (scanner.hasNextLine()) {
            String x = scanner.nextLine();
            textoGetHostsResponse = textoGetHostsResponse + x + "\n";
        }

        String parte1 = textoGetHostsResponse.split("</getHostsResponse>")[0];
        String parte2 = "</getHostsResponse>" + textoGetHostsResponse.split("</getHostsResponse>")[1];
        textoGetHostsResponse = parte1;
        for (int i = 0; i < listaHosts.size(); i++) {
            textoGetHostsResponse = textoGetHostsResponse + "<host>" + "\n"
                    + "<ip>" + listaHosts.get(i).getIp() + "</ip>" + "\n"
                    + "<port>" + listaHosts.get(i).getPorta() + "</port>" + "\n" + "</host>" + "\n";
        }
        textoGetHostsResponse = textoGetHostsResponse + "</getHostsResponse>" + "\n" + "</p2pse>";
        //System.out.println(textoGetHostsResponse);
        FileWriter arq = new FileWriter("E:\\Projeto_FileSharing\\Arquivos XML\\getHostResponse.xml");
        PrintWriter gravarArq = new PrintWriter(arq);

        gravarArq.printf(textoGetHostsResponse);
        arq.close();

    }

    public static void main(String args[]) throws IOException, ClassNotFoundException, Base64DecodingException {
        Controller c = new Controller();
        String[] arquivos = new String[1];
        arquivos[0] = "nome.txt";
        c.getFiles("127.0.0.1", 1234, arquivos);
        //c.atualizarHostsLocais("127.0.0.1", 1234);
        /*
         ArrayList hosts = c.getHosts("localhost", 1234);
        
         int i=0;
         for(i=0; i<hosts.size(); i++){
         System.out.print("Host " + (i+1) + "\n");
         System.out.print("Ip: " + ((Host)hosts.get(i)).getIp() + "\n");
         System.out.print("Porta: " + ((Host)hosts.get(i)).getPorta() + "\n");
         }
        
         ArrayList arquivos = c.searchFile("192.168.0.100", 1234);
         int i=0;
        
         for(i=0; i<arquivos.size(); i++){
         System.out.print("File " + (i+1) + "\n");
         System.out.print("Nome: " + ((Arquivo)arquivos.get(i)).getNome() + "\n");
         System.out.print("Tamanho: " + ((Arquivo)arquivos.get(i)).getTamanho() + "Bytes"+ "\n");
         }
               
         String[] nomes = {"histórico.pdf"};
         c.getFiles("127.0.0.1", 1234, nomes);
         System.out.print("Baixou o arquivo");
         */
        //c.downloadImagens("http://sites.ecomp.uefs.br/joao/", "C:\\Users\\Rafael Macedo\\Desktop\\2015.1\\Programação para Redes\\Problema02V2\\Files");
//        ArrayList hosts = c.getHosts("127.0.0.1", 1234);
//        for(int i=0; i<hosts.size(); i++){
//            System.out.print("Ip " + ((Host)hosts.get(i)).getIp());
//            System.out.print("Porta: " + ((Host)hosts.get(i)).getPorta());
//        }
//        ArrayList palavrasChave = new ArrayList();
//        //palavrasChave.add("carro");
//        palavrasChave.add("uefs");
//        ArrayList encontrados = c.searchFile("127.0.0.1", 1234, palavrasChave);
//        for(int i=0; i<encontrados.size(); i++){
//            System.out.print("Nome" + ((Arquivo)encontrados.get(i)).getNome());
//        }

    }

    public void downloadImagens(String stringUrl, String pathLocal) throws MalformedURLException, IOException {

        try {
            URL url = new URL(stringUrl);
            String nomeArquivoLocal = url.getPath();
            InputStream is = url.openStream();
            FileOutputStream fos = new FileOutputStream(pathLocal + nomeArquivoLocal);
            int umByte = 0;
            while ((umByte = is.read()) != -1) {
                fos.write(umByte);
            }
            is.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

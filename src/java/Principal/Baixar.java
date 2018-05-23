/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rebeca
 */
public class Baixar {

    static int c = 0;

    public static void main(String[] args) throws IOException {
        Baixar ex = new Baixar();
        ex.iniciar("http://gshow.globo.com/receitas/");

    }

    public static void iniciar(String link) {

        try {
            Elements links = criaElemento(link, "a");
            Elements imagens = criaElemento(link, "img");
            salvar(links, "href");
            salvar(imagens, "src");
        } catch (Exception ex1) {
            Logger.getLogger(Baixar.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    public static Elements criaElemento(String link, String tipo) throws IOException {

        Document doc = Jsoup.connect(link).get();
        Elements imagens = doc.select(tipo);
        return imagens;

    }

    public static void salvar(Elements elementos, String tipo) throws Exception {

        // Se for do tipo link 
        if (tipo.equals("href")) {
            // busca imagens 
            for (Element element : elementos) {

                String link = element.attr(tipo);
                //System.out.println(link + "link href");
                if (testaLink(link)) { //testa se Ã© um link no contexto
                    //System.out.println("Ã© no contexto");
                    Elements imagens = criaElemento(link, "img"); //cria elementos para as imagens
                    //salva as imagens
                    for (Element elem : imagens) {
                        String linkImg = elem.attr("src");
                        
                        if (testaLink(linkImg)) {
                            System.out.println("Imagem no link: " + linkImg);
                            String ext = getExt(linkImg);
                            if (ext != null) {
                                get(linkImg, "imagem-count" + c + ext);
                                c++;
                            }
                        }
                    }
                }
            }
        } // se for do tipo imagem
        else if (tipo.equals("src")) {

            // salva
            for (Element elem : elementos) {
                String linkImg = elem.attr("src");
                if (testaLink(linkImg)) {
                    System.out.println("Imagem no link " + linkImg);
                    String ext = getExt(linkImg);
                    if (ext != null) {
                        get(linkImg, "imagem-count" + c + ext);
                        c++;
                    }
                }
            }
        }
    }

    private static String getExt(String linkImg) {
        if (linkImg.toLowerCase().contains(".gif")) {
            return ".gif";
        }
        if (linkImg.toLowerCase().contains(".jpg")) {
            return ".jpg";
        }
        return null;
    }
    
// Verifica se o link estÃ¡ dentro do contexto esperado

    private static boolean testaLink(String link) {
        if (link.toLowerCase().contains("massa") || (link.toLowerCase().contains("macarrao")) || (link.toLowerCase().contains("pizza"))
                || (link.toLowerCase().contains("pasta")) || (link.toLowerCase().contains("panqueca"))
                || (link.toLowerCase().contains("lasanha")) || (link.toLowerCase().contains("penne"))
                || (link.toLowerCase().contains("talharim")) || (link.toLowerCase().contains("macarronada"))
                || (link.toLowerCase().contains("empadao")) || (link.toLowerCase().contains("canelone"))
                || (link.toLowerCase().contains("nhoque")) || (link.toLowerCase().contains("canelone"))) {
            return true;
        }
        return false;

    }

    public static void get(String link, String fileName) throws Exception {

        URL url = new URL(link);

        String destino = "C:\\repositorio\\" + fileName;
        InputStream is = url.openStream();
        FileOutputStream fos = new FileOutputStream(destino);
        int bytes = 0;
        while ((bytes = is.read()) != -1) {
            fos.write(bytes);
        }
        is.close();
        fos.close();
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletJsp;

import Host.Arquivo;
import Host.Host;
import JSPs.TesteJavaBean;
import Principal.Controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rafael Macedo
 */
@WebServlet(
        name = "Servlet",
        urlPatterns = {"/Servlet"},
        initParams = {
            @WebInitParam(name = "palavra", value = ""),
            @WebInitParam(name = "ip", value = ""),
            @WebInitParam(name = "porta", value = "")}
)
public class Servlet extends HttpServlet {

    Controller control = new Controller();
    Host host = new Host();

    @Override

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        String destino;
        ArrayList nomesArquivos = new ArrayList();

        //Busca arquivos locais
        if (acao.equals("1")) {
            List<Arquivo> listaArquivos = new ArrayList<Arquivo>();
            //nomesArquivos[1]= control.PalavraChave1;
            String palavraschave = request.getParameter("palavra");
            String[] palavras = palavraschave.split(",");
            for (int i = 0; i < palavras.length; i++) {
                nomesArquivos.add(palavras[i]);
                System.out.print(palavras[i]);
                
            }

            try {
                listaArquivos = control.searchFile("127.0.0.1", 1234, nomesArquivos);
                request.setAttribute("listaArquivos", listaArquivos);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            RequestDispatcher rd = request.getRequestDispatcher("BuscarArquivosLocais.jsp");
            rd.forward(request, response);

        } //Busca arquivos remotos
        else if (acao.equals("2")) {
            List<Arquivo> listaArquivos;
            String ip = request.getParameter("ip");
            int porta = Integer.parseInt(request.getParameter("porta"));
            String palavraschave = request.getParameter("palavra");
            String[] palavras = palavraschave.split(",");
            for (int i = 0; i < palavras.length; i++) {
                nomesArquivos.add(palavras[i]);
            }

            try {
                listaArquivos = control.searchFile(ip, porta, nomesArquivos);
                request.setAttribute("listaArquivos", listaArquivos);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            RequestDispatcher rd = request.getRequestDispatcher("BuscarArquivosRemotos.jsp");
            rd.forward(request, response);

        } //Atualizar arquivos locais
        else if (acao.equals("3")) {

        } // Lista Hosts locais
        else if (acao.equals("4")) {
            List<Host> listaHosts;

            try {
                listaHosts = control.getHosts("127.0.0.1", 1234);
//                listaHosts = new ArrayList<Host>();
//                Host host1 = new Host("123.12.12.12", 1234);
//                Host host2 = new Host("123.12.12.13", 1234);
//                listaHosts.add(host1);
//                listaHosts.add(host2);
                request.setAttribute("listaHosts", listaHosts);

                RequestDispatcher rd = request.getRequestDispatcher("listarHostsLocais.jsp");
                rd.forward(request, response);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } //Listar hosts remotos
        else if (acao.equals("5")) {
            List<Host> listaHosts;
            String ip = request.getParameter("ip");
            int porta = Integer.parseInt(request.getParameter("porta"));

            try {
                listaHosts = control.getHosts(ip, porta);

                request.setAttribute("listaHosts", listaHosts);

                RequestDispatcher rd = request.getRequestDispatcher("listarHostsRemotos.jsp");
                rd.forward(request, response);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } //atualiza arquivos remotos
        else if (acao.equals("6")) {

            List<Host> listaHosts;
            String ip = request.getParameter("ip");
            int porta = Integer.parseInt(request.getParameter("porta"));

            try {
                control.atualizarHostsLocais(ip, porta);

                RequestDispatcher rd = request.getRequestDispatcher("Mensagem.jsp");
                rd.forward(request, response);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}

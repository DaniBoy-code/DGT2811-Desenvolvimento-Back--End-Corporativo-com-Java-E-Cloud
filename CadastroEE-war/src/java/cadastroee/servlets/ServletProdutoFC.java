package cadastroee.servlets;

import cadastroee.controller.ProdutoFacadeLocal;
import cadastroee.model.Produto;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletProdutoFC", urlPatterns = {"/ServletProdutoFC"})
public class ServletProdutoFC extends HttpServlet {

    @EJB
    ProdutoFacadeLocal facade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        if (acao == null) {
            acao = "listar";
        }
        
        String destino = "ProdutoLista.jsp";

        try {
            if (acao.equals("listar")) {
                List<Produto> produtos = facade.findAll();
                request.setAttribute("produtos", produtos);
                
            } else if (acao.equals("formAlterar")) {
                Integer id = Integer.parseInt(request.getParameter("id"));
                Produto p = facade.find(id);
                request.setAttribute("produto", p);
                destino = "ProdutoDados.jsp";
                
            } else if (acao.equals("formIncluir")) {
                destino = "ProdutoDados.jsp";
                
            } else if (acao.equals("excluir")) {
                Integer id = Integer.parseInt(request.getParameter("id"));
                Produto p = facade.find(id);
                facade.remove(p);
                request.setAttribute("produtos", facade.findAll());
                
            } else if (acao.equals("alterar")) {
                Integer id = Integer.parseInt(request.getParameter("id"));
                String nome = request.getParameter("nome");
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                float preco = Float.parseFloat(request.getParameter("precoVenda"));
                
                Produto p = facade.find(id);
                p.setNome(nome);
                p.setQuantidade(quantidade);
                p.setPrecoVenda(preco);
                facade.edit(p);
                request.setAttribute("produtos", facade.findAll());
                
            } else if (acao.equals("incluir")) {
                String nome = request.getParameter("nome");
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                float preco = Float.parseFloat(request.getParameter("precoVenda"));
                
                Produto p = new Produto();
                p.setNome(nome);
                p.setQuantidade(quantidade);
                p.setPrecoVenda(preco);
                facade.create(p);
                request.setAttribute("produtos", facade.findAll());
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar acao: " + e.getMessage());
        }

        request.getRequestDispatcher(destino).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
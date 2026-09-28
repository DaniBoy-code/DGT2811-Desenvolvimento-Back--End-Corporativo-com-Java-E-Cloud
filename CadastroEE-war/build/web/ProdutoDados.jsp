<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cadastroee.model.Produto"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dados do Produto</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body class="container">
        <%
            Produto produto = (Produto) request.getAttribute("produto");
            String acao = (produto == null) ? "incluir" : "alterar";
            String titulo = (produto == null) ? "Incluir Produto" : "Alterar Produto";
        %>
        
        <h1 class="mt-4"><%= titulo %></h1>
        
        <form action="ServletProdutoFC" method="POST" class="form">
            <input type="hidden" name="acao" value="<%= acao %>">
            <% if (produto != null) { %>
                <input type="hidden" name="id" value="<%= produto.getId() %>">
            <% } %>
            
            <div class="mb-3">
                <label for="nome" class="form-label">Nome:</label>
                <input type="text" class="form-control" id="nome" name="nome" 
                       value="<%= produto != null ? produto.getNome() : "" %>" required>
            </div>
            
            <div class="mb-3">
                <label for="quantidade" class="form-label">Quantidade:</label>
                <input type="number" class="form-control" id="quantidade" name="quantidade" 
                       value="<%= produto != null ? produto.getQuantidade() : "" %>" required>
            </div>
            
            <div class="mb-3">
                <label for="precoVenda" class="form-label">Preço de Venda:</label>
                <input type="number" step="0.01" class="form-control" id="precoVenda" name="precoVenda" 
                       value="<%= produto != null ? produto.getPrecoVenda() : "" %>" required>
            </div>
            
            <button type="submit" class="btn btn-primary">Salvar</button>
            <a href="ServletProdutoFC?acao=listar" class="btn btn-secondary">Cancelar</a>
        </form>
    </body>
</html>
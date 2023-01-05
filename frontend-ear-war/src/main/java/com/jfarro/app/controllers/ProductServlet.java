package com.jfarro.app.controllers;

import com.jfarro.app.entities.Category;
import com.jfarro.app.entities.Client;
import com.jfarro.app.entities.Product;
import com.jfarro.app.services.ProductService;
import com.jfarro.app.util.CodeFormating;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/inventario/productos")
public class ProductServlet extends HttpServlet {

    @Inject
    private ProductService productService;

    @Inject
    private ProductController productController;

    @Inject
    private CodeFormating codeFormating;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = this.productService.findAllProducts();
        req.setAttribute("products", products);

        List<Category> categories = this.productService.findAllCategories();
        req.setAttribute("categories", categories);

        String code = "";
        //Verifica si se ha seleccionado el usuario para editar sus datos
        if (req.getParameter("id") != null) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (Exception ex) {
                id = 0;
            }
            Optional<Product> optionalUser = this.productService.findByIdProduct(id);
            if (optionalUser.isPresent()) {
                Product product = optionalUser.get();
                code = product.getCode();
                req.setAttribute("product", product);
                req.getSession().setAttribute("productEdit", product);
            }
        } else {
            code = this.codeFormating.productCode();
        }
        req.setAttribute("code", code);

        //Verifica si se selecciono el usuario a eliminar por id
        if (req.getParameter("idDelete") != null) {
            long id;
            try {
                id = Long.parseLong(req.getParameter("idDelete"));
            } catch (Exception ex) {
                id = 0;
            }
            this.productService.deleteProduct(id);
            resp.sendRedirect(req.getContextPath() + "/inventario/productos");
            return;
        }

        //Muestra los mensajes y datos
        if (req.getSession().getAttribute("messages") != null) {
            req.setAttribute("messages", req.getSession().getAttribute("messages"));
            req.getSession().removeAttribute("messages");
        }
        if (req.getSession().getAttribute("product") != null) {
            req.setAttribute("product", req.getSession().getAttribute("product"));
            req.getSession().removeAttribute("product");
        }

        getServletContext().getRequestDispatcher("/productos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int amount;
        try {
            amount = Integer.parseInt("amount");
        } catch (Exception ex) {
            amount = 0;
        }
        double price;
        try {
            price = Double.parseDouble("price");
        } catch (Exception ex) {
            price = 0;
        }
        Long idCategory;
        try {
            idCategory = Long.parseLong(req.getParameter("category"));
        } catch (Exception ex) {
            idCategory = null;
        }

        Product productEdit = null;
        if (req.getSession().getAttribute("productEdit") != null) {
            productEdit = (Product) req.getSession().getAttribute("productEdit");
            req.getSession().removeAttribute("productEdit");
        }

        Product product = new Product();
        product.setId(productEdit != null ? product.getId() : null);
        product.setName(name);
        product.setAmount(amount);
        product.setPrice(price);

        Optional<Category> optional = this.productService.findByIdCategory(idCategory);
        optional.ifPresent(product::setCategory);

        Map<String, String> messages = this.productController.dataValidationController(product, null);
        if (messages.isEmpty()) {
            messages.put("exito", "Producto guardado exitosamente");
            req.getSession().setAttribute("messages", messages);
            resp.sendRedirect(req.getContextPath() + "/inventario/productos");
        } else {
            req.getSession().setAttribute("messages", messages);
            req.getSession().setAttribute("product", product);
            //Si se esta editando paso el id por parametro
            if (productEdit != null) {
                resp.sendRedirect(req.getContextPath() + "/inventario/productos?id=" + productEdit.getId());
            } else {
                resp.sendRedirect(req.getContextPath() + "/inventario/productos");
            }
        }
    }
}

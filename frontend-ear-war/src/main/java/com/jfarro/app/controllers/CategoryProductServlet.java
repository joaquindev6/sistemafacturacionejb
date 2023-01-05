package com.jfarro.app.controllers;

import com.jfarro.app.entities.Category;
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

@WebServlet("/inventario/categorias")
public class CategoryProductServlet extends HttpServlet {

    @Inject
    private ProductService productService;

    @Inject
    private CategoryProductController categoryController;

    @Inject
    private CodeFormating codeFormating;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            Optional<Category> optionalCategory = this.productService.findByIdCategory(id);
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                code = category.getCode();
                req.setAttribute("category", category);
                req.getSession().setAttribute("categoryEdit", category);
            }
        } else {
            code = this.codeFormating.categoryProductCode();
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
            this.productService.deleteCategory(id);
            resp.sendRedirect(req.getContextPath() + "/inventario/categories");
            return;
        }

        //Muestra los mensajes y datos
        if (req.getSession().getAttribute("messages") != null) {
            req.setAttribute("messages", req.getSession().getAttribute("messages"));
            req.getSession().removeAttribute("messages");
        }
        if (req.getSession().getAttribute("category") != null) {
            req.setAttribute("category", req.getSession().getAttribute("category"));
            req.getSession().removeAttribute("category");
        }

        getServletContext().getRequestDispatcher("/producto-categorias.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        Category categoryEdit = null;
        if (req.getSession().getAttribute("categoryEdit") != null) {
            categoryEdit = (Category) req.getSession().getAttribute("categoryEdit");
            req.getSession().removeAttribute("categoryEdit");
        }

        Category category = new Category();
        category.setId(categoryEdit != null ? categoryEdit.getId() : null);
        category.setName(name);

        Map<String, String> messages = this.categoryController.dataValidationController(category, null);
        if (messages.isEmpty()) {
            messages.put("exito", "Categoría guardada exitosamente");
            req.getSession().setAttribute("messages", messages);
            resp.sendRedirect(req.getContextPath() + "/inventario/categories");
        } else {
            req.getSession().setAttribute("messages", messages);
            req.getSession().setAttribute("category", category);
            //Si se esta editando paso el id por parametro
            if (categoryEdit != null) {
                resp.sendRedirect(req.getContextPath() + "/inventario/categories?id=" + categoryEdit.getId());
            } else {
                resp.sendRedirect(req.getContextPath() + "/inventario/categories");
            }
        }
    }
}

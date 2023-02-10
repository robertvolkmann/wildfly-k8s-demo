package com.github.robertvolkmann.demo;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.wildfly.clustering.group.Group;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.time.Instant;

@WebServlet(urlPatterns = {"/"})
public class DemoServlet extends HttpServlet {

    public static final String PAGE_VISITS = "PageVisits";

    @Resource(lookup = "java:jboss/clustering/group/default")
    private Group group;

    private String pageTitle = "Wildfly K8s Demo";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initPageTitle(config);
    }

    private void initPageTitle(ServletConfig config) {
        var pageTitle = config.getInitParameter("pageTitle");
        if (pageTitle != null) {
            this.pageTitle = pageTitle;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        var session = request.getSession();
        var pageVisits = getPageVisits(request.getSession());
        pageVisits.increment();

        try (var out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + pageTitle + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + pageTitle + "</h1>");

            var table = new TableRowWriter(out);
            out.println("<table>");
            table.printRow("Number of Visits", pageVisits.getValue());
            table.printRow("Session ID", session.getId());
            table.printRow("Session Creation Time", Instant.ofEpochMilli(session.getCreationTime()));
            table.printRow("Session Last Access Time", Instant.ofEpochMilli(session.getLastAccessedTime()));

            table.printRow("Host Name", System.getenv("HOSTNAME"));
            table.printRow("Your IP Address", request.getRemoteAddr());

            table.printRow("Cluster Coordinator", group.getMembership().getCoordinator());
            table.printRow("Cluster Members", group.getMembership().getMembers());

            var runtime = ManagementFactory.getRuntimeMXBean();
            table.printRow("Java VM Name", runtime.getVmName());
            table.printRow("Java VM Vendor", runtime.getVmVendor());
            table.printRow("Java VM Version", runtime.getSpecVersion());
            out.println("</table>");


            out.println("</body>");
            out.println("</html>");
        }
    }

    private PageVisits getPageVisits(HttpSession session) {
        var pageVisits = (PageVisits) session.getAttribute(PAGE_VISITS);

        if (pageVisits == null) {
            pageVisits = new PageVisits();
            session.setAttribute(PAGE_VISITS, pageVisits);
        }

        return pageVisits;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return pageTitle;
    }

}

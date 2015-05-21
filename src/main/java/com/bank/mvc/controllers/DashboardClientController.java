package com.bank.mvc.controllers;

import com.bank.mvc.domain.service.*;
import com.bank.mvc.models.Credit;
import com.bank.mvc.models.Organization;
import com.bank.mvc.models.Service;
import com.bank.mvc.models.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zalman on 14.05.2015.
 */

@Controller
@RequestMapping("/dashboard/client/*")
public class DashboardClientController {

    final static Logger logger = Logger.getLogger(DashboardClientController.class);

    private static String path = "/dashboard/client/";

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryServicesService categoryServicesService;

    @Autowired
    private UtilitiesService utilitiesService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private CreditService creditService;

    @Autowired
    MessageSource msgSrc;

    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String dashboardClientMain(Model model) {
        logger.info("GET: " + path + "main");
        User user = getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("categoryServices", categoryServicesService.getAllCategoryServices());
        return "dashboard_client_main";
    }

    @RequestMapping(value = "transfer", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String dashboardClientTransfer(Model model) {
        logger.info("GET: " + path + "transfer");
        User user = getCurrentUser();
        model.addAttribute("user", user);
        return "dashboard_client_transfer";
    }

    @RequestMapping(value = "currency-exchange", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String dashboardClientCurrencyExchange(Model model) {
        logger.info("GET: " + path + "currency-exchange");
        User user = getCurrentUser();
        model.addAttribute("user", user);
        return "dashboard_client_currency_exchange";
    }

    @RequestMapping(value = "credit", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String dashboardClientCredit(Model model) {
        logger.info("GET: " + path + "credit");
        User user = getCurrentUser();
        model.addAttribute("user", user);
        return "dashboard_client_credit";
    }


    @RequestMapping(value = "credit-repayment", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String dashboardClientCreditRepayment(Model model) {
        logger.info("GET: " + path + "credit-repayment");
        User user = getCurrentUser();
        List<Credit> credits = (ArrayList<Credit>)creditService.getCreditsByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("credits", credits);
        return "dashboard_client_credit_repayment";
    }

    @RequestMapping(value = "payment-services/{organizationId}", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String dashboardClientPaymentServices(@PathVariable("organizationId") int organizationId, Model model) {
        logger.info("GET: " + path + "payment-services/" + organizationId);
        User user = getCurrentUser();
        model.addAttribute("user", user);
        Organization organization = organizationService.getOrganizationById(organizationId);
        model.addAttribute("organization", organization);
        return organization == null ? "404" : "dashboard_client_payment_services";
    }

    @RequestMapping(value = "services", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String dashboardClientServices(Model model) {
        logger.info("GET: " + path + "services");
        User user = getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("categoryServices", categoryServicesService.getAllCategoryServices());
        return "dashboard_client_services";
    }

    @RequestMapping("service/{serviceId}")
    public String dashboardClientService(@PathVariable("serviceId") int serviceId, Model model) {
        logger.info("GET: " + path + "service" + serviceId);
        Service service = utilitiesService.getServiceById(serviceId);
        model.addAttribute("service", service);
        return service.getOrganizations().isEmpty() ? "404" : "dashboard_client_organizations";
    }

    private User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // deprecated (update user)
        return userService.getUserById(user.getId());
    }

}

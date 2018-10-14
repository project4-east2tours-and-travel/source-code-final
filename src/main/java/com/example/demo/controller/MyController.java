package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Controller
public class MyController {
    private static String UPLOADED_FOLDER = "target/classes/static/uploaded/";

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private TourModel tourModel;

    @Autowired
    private TourDetailModel tourDetailModel;

    @Autowired
    private CustomerModel customerModel;

    @Autowired
    private OrderDetailModel orderDetailModel;

    @Autowired
    private OrderTourModel orderTourModel;

    private String emailAdmin="_";
    private String emailCus="_";
    private String pass="_";


    // Tour

    @RequestMapping(path = "/admin/tour/create-tour", method = RequestMethod.GET)
    public String createTour(Model model){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("tour", new Tour());
        return "add-tour";}
    }

    @RequestMapping(path = "/admin/tour/create-tour", method = RequestMethod.POST)
    public String addTour(@Valid Tour tour, BindingResult bindingResult, @RequestParam("myImg") MultipartFile myImg){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        tour.setImage("_");
        if (bindingResult.hasErrors()) {
            return "new";
        }try {
            Path path= Paths.get(UPLOADED_FOLDER + myImg.getOriginalFilename());
            Files.write(path, myImg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tour.setImage("/uploaded/" + myImg.getOriginalFilename());
        tourModel.save(tour);
        return "redirect:/admin/tour/list-tour";}
    }

    @RequestMapping(path = "/admin/tour/list-tour", method = RequestMethod.GET)
    public String getListTour(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("pagination", tourModel.findByStatusIsNot(0,PageRequest.of(page - 1,limit)));
        model.addAttribute("page",page);
        model.addAttribute("limit",limit);
        return "list-tour";}
    }

    @RequestMapping(path = "/admin/tour/listall-tour", method = RequestMethod.GET)
    public String getListAllTour(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("pagination", tourModel.findByStatus(0,PageRequest.of(page - 1,limit)));
        model.addAttribute("page",page);
        model.addAttribute("limit",limit);
        return "listall-tour";}
    }

    @RequestMapping(path="/admin/tour/edit-tour/{ma}", method = RequestMethod.GET)
    public String editTour(@PathVariable int ma, Model model){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Tour> opProduct = tourModel.findById(ma);
        if (opProduct.isPresent()){
            model.addAttribute("tour", opProduct.get());
            return "add-tour";
        }
        return "not-found";}
    }

    @RequestMapping(path = "/admin/tour/delete-tour/{ma}", method = RequestMethod.GET)
    public String deleteTour(@PathVariable int ma){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Tour> opProduct = tourModel.findById(ma);
        opProduct.get().setStatus(0);
        tourModel.save(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/list-tour";
        }
        return "not-found";}
    }

    @RequestMapping(path = "/admin/tour/remove-tour/{ma}", method = RequestMethod.GET)
    public String removeTour(@PathVariable int ma){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Tour> opProduct = tourModel.findById(ma);
        tourModel.delete(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/listall-tour";
        }
        return "not-found";}
    }

    @RequestMapping(path="/tour/detail/tour/{ma}", method = RequestMethod.GET)
    public String detailTour(@PathVariable int ma, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
        Optional<Tour> opProduct = tourModel.findById(ma);
        if (opProduct.isPresent()){
            model.addAttribute("product", opProduct.get());
            String name=opProduct.get().getName();

            model.addAttribute("pagination2",tourDetailModel.findByNameStartsWithAndStatusIsNot(name,0, PageRequest.of(page-1,limit)));
            model.addAttribute("page2",page);
            model.addAttribute("limit2",limit);
            return "detail-tour";
        }
        return "not-found";
    }





    // Tour Detail

    @RequestMapping(path = "/admin/tour/create-tour-detail", method = RequestMethod.GET)
    public String createTourDetail(Model model){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("tour_detail", new Tour_detail());
        return "add-tour_detail";}
    }

    @RequestMapping(path = "/admin/tour/create-tour-detail", method = RequestMethod.POST)
    public String addTourDetail(@Valid Tour_detail p, BindingResult bindingResult, @RequestParam("myImg") MultipartFile myImg){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        p.setRemaining_ticket(0);
        p.setImage("_");
        if (bindingResult.hasErrors()) {
            return "new";
        }try {
            Path path= Paths.get(UPLOADED_FOLDER + myImg.getOriginalFilename());
            Files.write(path, myImg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.setImage("/uploaded/" + myImg.getOriginalFilename());

        tourDetailModel.save(p);
        return "redirect:/admin/tour/list-tour-detail";}
    }

    @RequestMapping(path = "/admin/tour/list-tour-detail", method = RequestMethod.GET)
    public String getListTourDetail(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
//        long millis=System.currentTimeMillis();
//        java.sql.Date date=new java.sql.Date(millis);
//        for (Tour_detail tour_detail : tourDetailModel.findByStatusIsNot(0,PageRequest.of(page-1,limit))) {
//            if (tour_detail.getEndDay().before(date)==true){
//                tour_detail.setStatus(0);
//                tourDetailModel.save(tour_detail);
//            }
//        }
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        for (Tour_detail tour_detail : tourDetailModel.findByStatusIsNot(0,PageRequest.of(page-1,limit))) {
            tour_detail.setRemaining_ticket(tour_detail.getQuantity_ticket());
            tourDetailModel.save(tour_detail);
        }
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        long date1 = date.getTime();
        for (Tour_detail tour_detail : tourDetailModel.findByStatusIsNot(0,PageRequest.of(page-1,limit))) {
            if ((tour_detail.getStartDay().getTime()-date1)<432000){
                tour_detail.setStatus(0);
                tourDetailModel.save(tour_detail);
            }
        }
        model.addAttribute("pagination2",tourDetailModel.findByStatusIsNot(0, PageRequest.of(page-1,limit)));
        model.addAttribute("page2",page);
        model.addAttribute("limit2",limit);
        return "list-tour-detail";}
    }

    @RequestMapping(path = "/admin/tour/listall-tour-detail", method = RequestMethod.GET)
    public String getAllListTourDetail(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("pagination2",tourDetailModel.findByStatus(0, PageRequest.of(page-1,limit)));
        model.addAttribute("page2",page);
        model.addAttribute("limit2",limit);
        return "listall-tour-detail";}
    }

    Customer customer_order_detail=new Customer();
    Tour_detail tour_detail_order_detail=new Tour_detail();

    @RequestMapping(path="/tour/detail/tour-detail/{ma}", method = RequestMethod.GET)
    public String detailTourDetail(@PathVariable int ma, Model model){
        String a="";
        for (Customer customer : customerModel.findAll()) {
            if (customer.getEmail().equals(emailCus) && customer.getPassword().equals(pass)){
//                a=String.valueOf(customer.getId_customer());
                customer_order_detail=customer;
            }
        }
        Optional<Tour_detail> opProduct = tourDetailModel.findById(ma);
        if (opProduct.isPresent()){
            model.addAttribute("product", opProduct.get());
            model.addAttribute("customer", customer_order_detail);
            tour_detail_order_detail=opProduct.get();
            return "detail-tour_detail";
        }
        return "not-found";
//        if(a==""){
//            String message0000="Login Failed";
//            model.addAttribute("message",message0000);
//            return "redirect:/login";
//        }
//        else{
//
//            Optional<Tour_detail> opProduct = tourDetailModel.findById(ma);
//            Optional<Customer> customer1=customerModel.findById(Integer.parseInt(a));
//            if (opProduct.isPresent()){
//                model.addAttribute("product", opProduct.get());
//                model.addAttribute("customer", customer1.get());
//                customer_order_detail=customer1.get();
//                tour_detail_order_detail=opProduct.get();
//                return "detail-tour_detail";
//            }

//        }
    }

    @RequestMapping(path="/admin/tour/tour-detail/description/{ma}", method = RequestMethod.GET)
    public String detaildesTourDetail(@PathVariable int ma, Model model){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Tour_detail> opProduct = tourDetailModel.findById(ma);
        if (opProduct.isPresent()){
            model.addAttribute("product", opProduct.get());
            return "new";
        }
        return "not-found";}
    }

    @RequestMapping(path="/tour/booking", method = RequestMethod.POST)
    public String detailTourDetailPost(@Valid Order_detail p, BindingResult bindingResult,
                                       @RequestParam("quantity") String quantity){
        if (bindingResult.hasErrors()) {
            return "new";
        }
        int quan= Integer.parseInt(quantity);
        java.sql.Date dateOrder=new java.sql.Date(System.currentTimeMillis());
        p.setOrder_Date(dateOrder);
        p.setStatus(1);
        p.setQuantity(quan);
        p.setPrice(quan * tour_detail_order_detail.getPrice());
        p.setExpired_Date(tour_detail_order_detail.getEndDay());
        p.setIdTourdetail(tour_detail_order_detail.getId_tourdetail());
        p.setIdCustomer(customer_order_detail.getId_customer());

        p.setName(tour_detail_order_detail.getName());
        p.setDeparture(tour_detail_order_detail.getDeparture());
        p.setImage(tour_detail_order_detail.getImage());
        p.setStartDay(tour_detail_order_detail.getStartDay());

        orderDetailModel.save(p);

        Optional<Tour_detail> opProduct = tourDetailModel.findById(tour_detail_order_detail.getId_tourdetail());
        opProduct.get().setRemaining_ticket(opProduct.get().getRemaining_ticket()-quan);
        tourDetailModel.save(opProduct.get());

        tour_detail_order_detail=new Tour_detail();
        return "redirect:/tour/list-tour-customer";
    }

    @RequestMapping(path = "/tour/list-tour-customer", method = RequestMethod.GET)
    public String getListTourCus(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "500") int limit, @RequestParam(defaultValue = "1") int page2, @RequestParam(defaultValue = "500") int limit2){
        if (customer_order_detail.getId_customer()==0){
            String message0000="Invite you to login";
            model.addAttribute("message",message0000);
            return "redirect:/login";
        }
        ArrayList<Order_tour> order_tours=new ArrayList<>();
        for (Order_detail order_detail : orderDetailModel.findByIdCustomerAndStatus(customer_order_detail.getId_customer(),3, PageRequest.of(page-1,limit)) ) {
            for (Order_tour order_tour : orderTourModel.findByIdOrderdetail(order_detail.getIdOrderdetail(),PageRequest.of(page-1,limit))) {
                order_tours.add(order_tour);
            }
        }
        model.addAttribute("pagination", orderDetailModel.findByIdCustomerAndStatus(customer_order_detail.getId_customer(),1, PageRequest.of(page-1,limit)));
        model.addAttribute("pagination2", orderDetailModel.findByIdCustomerAndStatus(customer_order_detail.getId_customer(),3, PageRequest.of(page2-1,limit2)));
        model.addAttribute("pagination3", orderDetailModel.findByIdCustomerAndStatus(customer_order_detail.getId_customer(),-1, PageRequest.of(page-1,limit)));
        model.addAttribute("order_tours", order_tours);
        return "list-tour-customer";
    }

    @RequestMapping(path = "/tour/listall-tour-customer", method = RequestMethod.GET)
    public String getAllListTourCus(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "50") int limit){
        if (customer_order_detail.getId_customer()==0){
            String message0000="Invite you to login";
            model.addAttribute("message",message0000);
            return "redirect:/login";
        }
        model.addAttribute("pagination", orderDetailModel.findByIdCustomerAndStatusIsNot(customer_order_detail.getId_customer(),0, PageRequest.of(page-1,limit)));
        return "list-tour-customer";
    }

    @RequestMapping(path = "/tour/my-account", method = RequestMethod.GET)
    public String myAccount(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "50") int limit){
        if (customer_order_detail.getId_customer()==0){
            String message0000="Invite you to login";
            model.addAttribute("message",message0000);
            return "redirect:/login";
        }
        String var="0"+ customerModel.findById(customer_order_detail.getId_customer()).get().getTelephone();
        model.addAttribute("var", var);
        model.addAttribute("customer",customerModel.findById(customer_order_detail.getId_customer()).get());
        return "my-account";
    }

    @RequestMapping(path = "/edit-pass", method = RequestMethod.POST)
    public String editpass(Model model,@RequestParam("pass") String pass,@RequestParam("pass1") String pass1){
        Optional<Customer> opProduct = customerModel.findById(customer_order_detail.getId_customer());
        if (opProduct.get().getPassword().equals(pass)){
            opProduct.get().setPassword(pass1);
            customerModel.save(opProduct.get());

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(opProduct.get().getEmail());
            message.setSubject("We Are East2WestTours and Travels");
            message.setText("Hello, You are edit password success ! ");

            // Send Message!
            this.emailSender.send(message);
            return "redirect:/tour/my-account";
        }

        String message0000="Change your password failed, invite you login";
        model.addAttribute("message",message0000);
        return "redirect:/login";
    }

    @RequestMapping(path = "/delete-tour/{ma}", method = RequestMethod.GET)
    public String delete(@PathVariable int ma){
        Optional<Order_detail> opProduct = orderDetailModel.findById(ma);
        opProduct.get().setStatus(0);
        orderDetailModel.save(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/tour/list-tour-customer";
        }
        return "not-found";
    }

    @RequestMapping(path = "/remove-tour/{ma}", method = RequestMethod.GET)
    public String remove(@PathVariable int ma){
        Optional<Order_detail> opProduct = orderDetailModel.findById(ma);
        orderDetailModel.delete(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/tour/list-tour-customer";
        }
        return "not-found";
    }

    Order_detail payOrder_detail= new Order_detail();

    @RequestMapping(path = "/pay-tour/{ma}", method = RequestMethod.GET)
    public String pay(@PathVariable int ma, Model model){
        Optional<Order_detail> opProduct = orderDetailModel.findById(ma);
        if (opProduct.isPresent()){
            payOrder_detail=opProduct.get();
            model.addAttribute("order_tour", new Order_tour());
            model.addAttribute("order", opProduct.get());
            model.addAttribute("id", opProduct.get().getIdOrderdetail());
            return "test";
        }
        return "not-found";
    }

    @RequestMapping(path = "/paypal-tour", method = RequestMethod.POST)
    public String paypal(@Valid Order_tour order_tour, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "new";
        }

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        order_tour.setPayDate(date);
        order_tour.setIdOrderdetail(payOrder_detail.getIdOrderdetail());
        order_tour.setStatus(1);
        orderTourModel.save(order_tour);

        Optional<Order_detail> opProduct = orderDetailModel.findById(payOrder_detail.getIdOrderdetail());
        opProduct.get().setStatus(3);
        orderDetailModel.save(opProduct.get());

        Optional<Customer> customer = customerModel.findById(opProduct.get().getIdCustomer());
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(customer.get().getEmail());
        message.setSubject("We Are East2WestTours and Travels");
        message.setText("Hello, You have set tour successfully ! Departure date is "+ opProduct.get().getExpired_Date()+". We invite you to visit the company at 8, Ton That Thuyet, My Dinh, Ha Noi, Viet Nam at 9:00 AM to make the tour");
        this.emailSender.send(message);

        payOrder_detail= new Order_detail();
        return "redirect:/tour/list-tour-customer";
    }


    @RequestMapping(path="/admin/tour/edit-tour-detail/{ma}", method = RequestMethod.GET)
    public String editTourDetail(@PathVariable int ma, Model model){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Tour_detail> opProduct = tourDetailModel.findById(ma);
        if (opProduct.isPresent()){
            model.addAttribute("tour_detail", opProduct.get());
            return "add-tour_detail";
        }return "not-found";
        }
    }

    @RequestMapping(path = "/admin/tour/delete-tour-detail/{ma}", method = RequestMethod.GET)
    public String deleteTourDetail(@PathVariable int ma){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Tour_detail> opProduct = tourDetailModel.findById(ma);
        opProduct.get().setStatus(0);
        tourDetailModel.save(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/list-tour-detail";
        }
        return "not-found";}
    }

    @RequestMapping(path = "/admin/tour/remove-tour-detail/{ma}", method = RequestMethod.GET)
    public String removeTourDetail(@PathVariable int ma){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Tour_detail> opProduct = tourDetailModel.findById(ma);
        tourDetailModel.delete(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/listall-tour-detail";
        }
        return "not-found";}
    }



    // Customer

    @RequestMapping(path = "/admin/tour/list-customer", method = RequestMethod.GET)
    public String getListCustomer(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("pagination2",customerModel.findByStatusIsNot(0, PageRequest.of(page-1,limit)));
        model.addAttribute("page2",page);
        model.addAttribute("limit2",limit);
        return "list-customer";}
    }

    @RequestMapping(path = "/admin/list-customer", method = RequestMethod.GET)
    public String getListCustomer2(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("pagination2",customerModel.findByStatusIsNot(0, PageRequest.of(page-1,limit)));
        model.addAttribute("page2",page);
        model.addAttribute("limit2",limit);
        return "customer";}
    }

    @RequestMapping(path = "/admin/tour/listall-customer", method = RequestMethod.GET)
    public String getAllListCustomer(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("pagination2",customerModel.findByStatusIsNot(1, PageRequest.of(page-1,limit)));
        model.addAttribute("page2",page);
        model.addAttribute("limit2",limit);
        return "listall-customer";}
    }

    @RequestMapping(path = "/admin/tour/delete-customer/{ma}", method = RequestMethod.GET)
    public String deleteCustomer(@PathVariable int ma){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Customer> opProduct = customerModel.findById(ma);
        opProduct.get().setStatus(0);
        customerModel.save(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/list-customer";
        }
        return "not-found";}
    }

    @RequestMapping(path = "/admin/tour/remove-customer/{ma}", method = RequestMethod.GET)
    public String removeCustomer(@PathVariable int ma){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Customer> opProduct = customerModel.findById(ma);
        customerModel.delete(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/list-customer";
        }
        return "not-found";}
    }

    @RequestMapping(path = "/admin/tour/action-customer/{ma}", method = RequestMethod.GET)
    public String actionCustomer(@PathVariable int ma){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Customer> opProduct = customerModel.findById(ma);
        opProduct.get().setStatus(1);
        customerModel.save(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/listall-customer";
        }
        return "not-found";}
    }


    // login

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login( Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "500") int limit){
        emailCus="_";
        emailAdmin="_";
        pass="_";
        customer_order_detail=new Customer();

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        long date1 = date.getTime();
        for (Tour_detail tour_detail : tourDetailModel.findByStatusIsNot(0,PageRequest.of(page-1,limit))) {
            if ((tour_detail.getStartDay().getTime()-date1)<432000){
                tour_detail.setStatus(0);
                tourDetailModel.save(tour_detail);
            }
        }

        String message="";
        model.addAttribute("message", message);
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String logintour(Model model, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
        if (email.equals("admin@gmail.com") && password.equals("12345")){
            emailAdmin="admin";
            return "redirect:/admin/tour/home";
        }
        else{
            for (Customer customer : customerModel.findByStatusIsNot(0,PageRequest.of(page-1,limit))) {
                if (customer.getEmail().equals(email) && customer.getPassword().equals(password)){
                    emailCus=email;
                    pass=password;
                    customer_order_detail=customer;
                    model.addAttribute("customer", customer);
                    ArrayList<Tour> tours=new ArrayList<>();
                    for (Tour tour :tourModel.findByStatusIsNot(0,PageRequest.of(page - 1,limit))){
                        if (tours.size()< 04){
                            tours.add(tour);
                        }else
                            break;
                    }
                    model.addAttribute("pagination",tours);
                    return "redirect:/";
                }
            }String message="Login Failed";
            model.addAttribute("message", message);
            return "login";
        }
    }

    @RequestMapping(path = "/message-customer", method = RequestMethod.POST)
    public String messageCus(Model model, @RequestParam("fullname") String fullname,
                             @RequestParam("message") String message,
                             @RequestParam("email") String email){

        if (email!=""){
            SimpleMailMessage message1 = new SimpleMailMessage();
            message1.setTo(email);
            message1.setSubject("We Are East2WestTours and Travels");
            message1.setText("Hello, Thank you for sending us feedback, we will carefully review your feedback to enhance company service, thank you very much.");
            this.emailSender.send(message1);
        }

        SimpleMailMessage message2 = new SimpleMailMessage();

        message2.setTo("dacnhd00302@fpt.edu.vn");
        message2.setSubject("East2WestTours and Travels");
        message2.setText("Hello Admin, This is a comment from the customer. Name Customer : " + fullname +". Email Customer : " + email+". Message : " +message + "!");
        this.emailSender.send(message2);

        return "redirect:/";
    }

//    @RequestMapping(path = "/admin/tour/create-tour", method = RequestMethod.GET)
//    public String createTour(Model model){
//        model.addAttribute("tour", new Tour());
//        return "add-tour";
//    }

    @RequestMapping(path = "/newaccount", method = RequestMethod.POST)
    public String addCustomer(@Valid Customer customer,Model model, BindingResult bindingResult,
                              @RequestParam("name") String name,
                              @RequestParam("email") String email,
                              @RequestParam("pw1") String password,
                              @RequestParam("telephone") int telephone){
        if (bindingResult.hasErrors()) {
            return "new";
        }
        for (Customer customer111 : customerModel.findAll()) {
            if (customer111.getEmail().equals(email)){
                String message000="Create new account failed";
                model.addAttribute("message", message000);
                return "redirect:/login";
            }
        }

        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setTelephone(telephone);
        customer.setStatus(1);
        customerModel.save(customer);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("We Are East2WestTours and Travels");
        message.setText("Hello, You are register new account success ! Welcome to East2WestTours and Travels");
        this.emailSender.send(message);

        String message000="Create new account successfully";
        model.addAttribute("message", message000);
        return "redirect:/login";
    }

    @RequestMapping(path = "/forgotpass", method = RequestMethod.POST)
    public String forgotpass(Model model, @RequestParam("email") String email, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "115") int limit){
        for (Customer customer : customerModel.findByStatusIsNot(0,PageRequest.of(page-1,limit))) {
            if (customer.getEmail().equals(email)){
                Random rd = new Random();   // khai báo 1 đối tượng Random
                int number = rd.nextInt();  // trả về 1 số nguyên bất kỳ
                String num= String.valueOf(number);
                customer.setPassword(num);
                customerModel.save(customer);

                SimpleMailMessage message = new SimpleMailMessage();

                message.setTo(customer.getEmail());
                message.setSubject("We Are East2WestTours and Travels");
                message.setText("Hello, You have successfully recovered your password, your new password is "+ number +", please login and change your password");
                this.emailSender.send(message);

                String message000="Change password successfully, invite you to access email to receive new password";
                model.addAttribute("message", message000);
                return "redirect:/login";
            }
        }
        String message000="Wrong Email Address";
        model.addAttribute("message", message000);
        return "redirect:/login";
    }


    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(){
        emailAdmin="_";
        emailCus="_";
        pass="_";
        customer_order_detail=new Customer();
        return "login";
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public String getListTourInIndex(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "50") int limit){
//        ArrayList<Tour> tours=new ArrayList<>();
//        for (Tour tour :tourModel.findByStatusIsNot(0,PageRequest.of(page - 1,limit))){
//            if (tours.size()< 04){
//                tours.add(tour);
//            }else
//                break;
//        }
//        model.addAttribute("pagination",tours);

//        nếu ngày bắt đầu không sau ngày hiện tại 5 ngày
        java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
        long date1 = date.getTime();
        for (Tour_detail tour_detail : tourDetailModel.findByStatusIsNot(0,PageRequest.of(page-1,limit))) {
            if (((tour_detail.getStartDay().getTime()-date1)<432000)==true){
                tour_detail.setStatus(0);
                tourDetailModel.save(tour_detail);
            }
        }
//        nếu hết vé
        for (Tour_detail tour_detail : tourDetailModel.findByStatusIsNot(0,PageRequest.of(page-1,limit))) {
            if (tour_detail.getRemaining_ticket()==0){
                tour_detail.setStatus(0);
                tourDetailModel.save(tour_detail);
            }
        }

//        trước 3 ngày hết hạn gửi mail, 2 ngày truóc ngày hết hạn hủy tour
        for (Order_detail order_detail : orderDetailModel.findByStatus(1,PageRequest.of(page-1,limit))) {
            if (((order_detail.getStartDay().getTime()-date1)<259200)==true){
                if (((order_detail.getStartDay().getTime()-date1)<172800)==true){
                    order_detail.setStatus(-1);
                    orderDetailModel.save(order_detail);
                }
                else{
                    order_detail.setStatus(-100);
                    orderDetailModel.save(order_detail);
//                    Optional<Customer> customer= customerModel.findById(order_detail.getIdCustomer());
//
//                    SimpleMailMessage message = new SimpleMailMessage();
//                    message.setTo(customer.get().getEmail());
//                    message.setSubject("We Are East2WestTours and Travels");
//                    message.setText("Hello, Your tour is about to expire, you need to pay for your trip. Tomorrow, we will cancel your trip if you have not pay. Thank you !");
//                    this.emailSender.send(message);
                }
            }
        }
        return "index";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getIndex(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "50") int limit){
//
////        nếu ngày bắt đầu không sau ngày hiện tại 5 ngày
//        java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
//        long date1 = date.getTime();
//        for (Tour_detail tour_detail : tourDetailModel.findByStatusIsNot(0,PageRequest.of(page-1,limit))) {
//            if ((tour_detail.getStartDay().getTime()-date1)<432000){
//                tour_detail.setStatus(0);
//                tourDetailModel.save(tour_detail);
//            }
//        }
////        nếu hết vé
//        for (Tour_detail tour_detail : tourDetailModel.findByStatusIsNot(0,PageRequest.of(page-1,limit))) {
//            if (tour_detail.getRemaining_ticket()==0){
//                tour_detail.setStatus(0);
//                tourDetailModel.save(tour_detail);
//            }
//        }
//
////        trước 3 ngày hết hạn gửi mail, 2 ngày truóc ngày hết hạn hủy tour
//        for (Order_detail order_detail : orderDetailModel.findByStatus(1,PageRequest.of(page-1,limit))) {
//            if ((order_detail.getExpired_Date().getTime()-date1)<259200){
//                if ((order_detail.getExpired_Date().getTime()-date1)<172800){
//                    order_detail.setStatus(-1);
//                    orderDetailModel.save(order_detail);
//                }
//                else{
//                    order_detail.setStatus(-100);
//                    orderDetailModel.save(order_detail);
////                    Optional<Customer> customer= customerModel.findById(order_detail.getIdCustomer());
////
////                    SimpleMailMessage message = new SimpleMailMessage();
////                    message.setTo(customer.get().getEmail());
////                    message.setSubject("We Are East2WestTours and Travels");
////                    message.setText("Hello, Your tour is about to expire, you need to pay for your trip. Tomorrow, we will cancel your trip if you have not pay. Thank you !");
////                    this.emailSender.send(message);
//                }
//            }
//        }
        return "redirect:/home";
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newPage(){
        return "new";
    }

    @RequestMapping(path = "/admin/tour/home", method = RequestMethod.GET)
    public String booking(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("orderDetailModel",orderDetailModel.findByStatus(1, PageRequest.of(page-1,limit)));
        model.addAttribute("customerModel",customerModel.findByStatus(1, PageRequest.of(page-1,limit)));
        model.addAttribute("tourDetailModel",tourDetailModel.findByStatus(1, PageRequest.of(page-1,limit)));
        model.addAttribute("tourModel",tourModel.findByStatus(1, PageRequest.of(page-1,limit)));
        model.addAttribute("orderTourModel",orderTourModel.findByStatus(1, PageRequest.of(page-1,limit)));
        return "admin";}
    }
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String test(){
        return "test";
    }


    //order detail

    @RequestMapping(path = "/admin/tour/list-order-detail", method = RequestMethod.GET)
    public String listOrderDetail(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("pagination2",orderDetailModel.findByStatus(1, PageRequest.of(page-1,limit)));
        model.addAttribute("page2",page);
        model.addAttribute("limit2",limit);
        return "list-order-detail";}
    }

    @RequestMapping(path = "/admin/tour/listall-order-detail", method = RequestMethod.GET)
    public String listallOrderDetail(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("pagination2",orderDetailModel.findByStatus(0, PageRequest.of(page-1,limit)));
        model.addAttribute("page2",page);
        model.addAttribute("limit2",limit);
        return "listall-order-detail";}
    }

    @RequestMapping(path = "/admin/tour/delete-order-detail/{ma}", method = RequestMethod.GET)
    public String deleteOrderDetail(@PathVariable int ma){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Order_detail> opProduct = orderDetailModel.findById(ma);
        opProduct.get().setStatus(0);
        orderDetailModel.save(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/listall-order-detail";
        }
        return "not-found";}
    }

    @RequestMapping(path = "/admin/tour/remove-order-detail/{ma}", method = RequestMethod.GET)
    public String removeOrderDetail(@PathVariable int ma){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Order_detail> opProduct = orderDetailModel.findById(ma);
        orderDetailModel.delete(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/listall-order-detail";
        }
        return "not-found";}
    }

    //order tour

    @RequestMapping(path = "/admin/tour/list-order-tour", method = RequestMethod.GET)
    public String listOrderTour(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("pagination2",orderTourModel.findByStatus(1,PageRequest.of(page-1,limit)));
        model.addAttribute("page2",page);
        model.addAttribute("limit2",limit);
        return "list-order-tour";}
    }

    @RequestMapping(path = "/admin/tour/listall-order-tour", method = RequestMethod.GET)
    public String listallOrderTour(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        model.addAttribute("pagination2",orderTourModel.findByStatus(0,PageRequest.of(page-1,limit)));
        model.addAttribute("page2",page);
        model.addAttribute("limit2",limit);
        return "listall-order-tour";}
    }

    @RequestMapping(path = "/admin/tour/delete-order-tour/{ma}", method = RequestMethod.GET)
    public String deleteOrderTour(@PathVariable int ma){
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Order_tour> opProduct = orderTourModel.findById(ma);
        opProduct.get().setStatus(0);
        orderTourModel.save(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/list-order-tour";
        }
        return "not-found";}
    }

    @RequestMapping(path = "/admin/tour/remove-order-tour/{ma}", method = RequestMethod.GET)
    public String removeOrderTour(@PathVariable int ma) {
        if (emailAdmin.equals("admin")==false){
            return "not-found";
        }else {
        Optional<Order_tour> opProduct = orderTourModel.findById(ma);
        orderTourModel.delete(opProduct.get());
        if (opProduct.isPresent()){
            return "redirect:/admin/tour/listall-order-tour";
        }
        return "not-found";}
    }

    //search

    @RequestMapping(path="/tour/search", method = RequestMethod.POST)
    public String search(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit,
                                        @RequestParam("departure") String departure,
                                        @RequestParam("group") String group,
                                        @RequestParam("departure_date") java.sql.Date departure_date){

        ArrayList<Tour_detail> list=new ArrayList<>();
        for (Tour_detail tour_detail : tourDetailModel.findByDepartureIsAndNameStartsWithAndStatusIs(departure,group,1,PageRequest.of(page-1,limit))) {
            if (tour_detail.getStartDay().after(departure_date)){
                list.add(tour_detail);
            }

        }
        model.addAttribute("list",list);
        model.addAttribute("departure",departure);
        model.addAttribute("group",group);
        model.addAttribute("departure_date",departure_date);
        return "search";
    }



//    @RequestMapping(path = "/tour/search", method = RequestMethod.GET)
//    public String getsearch(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit){
//        model.addAttribute("pagination2",tourDetailModel.findAll( PageRequest.of(page-1,limit)));
//        model.addAttribute("page2",page);
//        model.addAttribute("limit2",limit);
//        return "search";
//    }

//    @RequestMapping(path="/booking/{ma}", method = RequestMethod.GET)
//    public String book(@PathVariable int ma, Model model){
//        String a="";
//        for (Customer customer : customerModel.findAll()) {
//            if (customer.getEmail().equals(emailCus) && customer.getPassword().equals(pass)){
//                a=String.valueOf(customer.getId_customer());
//            }
//        }
//        if(a==""){
//            return "redirect:/login";
//        }
//        else{
//
//            Optional<Tour_detail> opProduct = tourDetailModel.findById(ma);
//            Optional<Customer> customer1=customerModel.findById(Integer.parseInt(a));
//            if (opProduct.isPresent()){
//                model.addAttribute("product", opProduct.get());
//                model.addAttribute("customer", customer1.get());
//                customer_order_detail=customer1.get();
//                tour_detail_order_detail=opProduct.get();
//                return "booking";
//            }
//            return "not-found";
//        }
//
//    }
}

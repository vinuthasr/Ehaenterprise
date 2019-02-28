package com.elephant.service.customer;

//import static org.hamcrest.CoreMatchers.containsString;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

//import com.elephant.domain.roles.Role;
import org.hibernate.loader.custom.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.elephant.EhaEnterpriseApplication;
import com.elephant.constant.StatusCode;
import com.elephant.dao.address.AddressDaoRepository;
//import com.elephant.dao.cart.CartDao;
import com.elephant.dao.customer.CustomerDao;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.dao.role.RoleRepository;
import com.elephant.domain.address.AddressDomain;
//import com.elephant.domain.cart.CartDomain;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.roles.Role;
import com.elephant.domain.roles.RoleName;
import com.elephant.mapper.customer.CustomerMapper;
import com.elephant.model.customer.CustomerModel;
import com.elephant.model.mail.Mail;
import com.elephant.response.Response;
//import com.elephant.service.customer.CustomerServiceImpl.EmailAuth;
import com.elephant.utils.CommonUtils;
import com.elephant.utils.SmtpMailSender;
import com.elephant.constant.*;




@Service("customerservice")
public class CustomerServiceImpl implements CustomerService {
	  
	private JavaMailSender javaMailSender;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	CustomerMapper customerMapper;
	
	@Autowired
	SmtpMailSender smtpMailSender;
	
	@Autowired
	EmailService emailService;
	/*
	@Autowired
	CartDao cartDao;*/
	
	@Autowired
	CustomerRepository cr;
	
	@Autowired
	EntityManager em;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
	private AddressDaoRepository ar;
	
	@Autowired
	private RoleRepository rr;

    @Autowired
    private SpringTemplateEngine templateEngine;
    

    
//    @Autowired
//    private CustomerDomain customer;
	
	@Autowired
	public CustomerServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender=javaMailSender;
	}
	
	
	     //========================add customer=============================//
	
private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
private static Logger log = LoggerFactory.getLogger(EhaEnterpriseApplication.class);
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Response addCustomer(CustomerModel customerModel,HttpServletRequest request) throws Exception {
		
		
		
		

		try {
		   CustomerDomain customer = new CustomerDomain();  
			BeanUtils.copyProperties(customerModel, customer);
			 customer.setValitateCode(CommonUtils.generateRandomId());
			    customer.setActive(false);
			    //customer.setActiveUser(false);
			    customer.setPassword(CommonUtils.encriptString(customerModel.getPassword()));
			   // customer.setPassword(CommonUtils.encriptString(customerModel.getConfirmPassword()));
			   		    
			    Role userRole=rr.findByName("ROLE_USER");
			    
			    
			    List<Role> roles = new ArrayList<>();
			    
				roles.add(userRole);
			
				
				customer.setRoles(roles);
	
				
			    
			    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(customerModel.getPassword());
//				String hashedPassword1 = passwordEncoder.encode(customerModel.getConfirmPassword( ));
//			
			 customer.setPassword(hashedPassword );
//			 customer.setConfirmPassword(hashedPassword1);
//			 
			 //CartDomain cartDomain =new CartDomain();
			 
//			 Mail mail = new Mail();
//		        mail.setFrom("yenugubala.hari@gmail.com");
//		        mail.setTo(customer.getEmail());
//		        mail.setSubject("Hello  "+customer.getCustomerName());
//
//		        Map<String, Object> model = new HashMap<String, Object>();
//		        model.put("name", customer.getCustomerName());
//		        model.put("email", customer.getEmail());
//		        model.put("code", customer.getValitateCode());
//		        
//		        System.out.println(request.getServerPort());
//		       
//		       
//		        model.put("signature",request.getServerPort()+"/v1/confirm?email="+customer.getEmail()+"&validate="+customer.getValitateCode());
//		        mail.setModel(model);
//		        
//		        MimeMessage message = emailSender.createMimeMessage();
//		        MimeMessageHelper helper = new MimeMessageHelper(message,
//		                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//		                StandardCharsets.UTF_8.name());
//
//		       // helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));
//
//		        Context context = new Context();
//		        context.setVariables(mail.getModel());
//		        
//		        String html = templateEngine.process("registration-form", context);
//
//		        helper.setTo(mail.getTo());
//		        helper.setText(html, true);
//		        helper.setSubject(mail.getSubject());
//		        helper.setFrom(mail.getFrom());
//
//		        emailSender.send(message);

		       // emailService.sendSimpleMessage(mail);
		     
				

//			        
//			        System.out.println("mailsend successfully");
//			    	System.out.println("Done!");
					

			 
		        
		        
			 	customerDao.addCustomer(customer);
	            /*cartDomain.setCustomer(customer);
	            cartDao.createCart(cartDomain);
	            customer.setCartDomain(cartDomain);*/
	            Response response=customerDao.addCustomer(customer);
				return response;
	            
		 } catch (UnsupportedEncodingException ex) {
	            ex.printStackTrace();

	        } catch (MessagingException ex) {
	            ex.printStackTrace();
	        }
		return null;
	    }

	    


	//==========================get customer all ===========================//

	@Override
	public List<CustomerModel> getCustomers() throws Exception {
		
		try {
			List<CustomerDomain> customers = customerDao.getCustomers();
			return customerMapper.entityList(customers);
		} catch (Exception ex) {
			logger.info("Exception getCustomers:", ex);
		}
		
		return null;
	}


	//===========================get customer by Id========================//

	@Override
	public CustomerModel getCustomer(long customersId) throws Exception {
		try {
			CustomerModel customerModel = new CustomerModel();
			CustomerDomain customerDomain= customerDao.getCustomer(customersId);
			BeanUtils.copyProperties(customerDomain, customerModel);
			return customerModel;
		}catch(Exception e) {
			logger.info("Exception in Get customerid",e);
		
		return null;
		}
	}



	//============================delete customer==========================//
	@Override
	public Response deleteCustomer(long customersId) throws Exception {
		try {
			return customerDao.deleteCustomer(customersId);
		} catch (Exception e) {
			logger.info("Exception deleteCustomer:", e.getMessage());

			return null;
		}
			
	
	}

  //===============================update customer============================//
	@Override
	public Response updateCustomer(CustomerModel customerModel,Principal pr) throws Exception {
		try {
			CustomerDomain customer = new CustomerDomain();
			BeanUtils.copyProperties(customerModel, customer);
		
			Response response = customerDao.updateCustomer(customer,pr);
			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	
	}
	
	
	
//	@Override
//	public CustomerModel getValitateCode(String valitateCode) throws Exception {
//		try {
//			
//			CustomerModel customerModel = new CustomerModel();
//			CustomerDomain customerDomain= customerDao.getValitateCode(valitateCode);
//			BeanUtils.copyProperties(customerDomain, customerModel);
//			return customerModel;
//		}catch(Exception e) {
//			logger.info("Exception in Get customerid",e);
//			return null;
//	}

	//}

	@Override
	public List<CustomerModel> getcustomerByrollId(long rollId) {
		
		try {
			List<CustomerDomain> customers = customerDao.getcustomersByrollId(rollId);
			return customerMapper.entityList(customers);
		} catch (Exception ex) {
			logger.info("Exception getCustomers:", ex);
		}
	
		return null;
	}

	

	@Override
	public void getConfirm(String valitateCode, String email) {
		// TODO Auto-generated method stub
		try {
			
		
			
			if((cr.findByEmail(email).getValitateCode()).equals(valitateCode)) {
			
			if(cr.findByEmail(email).isActive()==false) {
				customerDao.getConfirm(valitateCode,email);
			;
			}
				else {
					throw new Exception("already user confirmed");
				
				
			}
			}
			else {
				throw new Exception("validate code not correct");
			}
 			
//			CustomerModel customerModel = new CustomerModel();
//		CustomerDomain customerDomain= customerDao.getConfirm(valitateCode);
		
		
			//BeanUtils.copyProperties(customerDomain, customerModel);
			//return null;
		}catch(Exception e) {
			logger.info("Exception in Get customerid",e);
		//return null;
	}

}

//
//	@Override
//	public String updateCust(CustomerModel customerModel) {
//		CustomerDomain customer = new CustomerDomain();
//	    
//		   
//		BeanUtils.copyProperties(customerModel, customer);
//		
//		customer.setActive(true);
//		
//		String st= customerDao.updateCust(customer);
//		return st;
//		
//		
//	}


	@Override
	public CustomerModel authenticate(CustomerModel customerModel) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(customerModel.getPassword());
		
		System.out.println(hashedPassword);
		
			
			CustomerDomain customer = new CustomerDomain();
			BeanUtils.copyProperties(customerModel,customer);
			//customer.setActiveUser(true);
			customer = customerDao.auteneticate(customer);
			if (customer == null)
				return null;
		
			
			BeanUtils.copyProperties(customer, customerModel);
			List<AddressDomain> addressDomain=cr.findByEmail(customer.getEmail()).getAddressDomain();
			customer.setAddressDomain(addressDomain);
//			for(Object i:customer1) {
//			System.out.println(i.toString());
//			}
			
			return customerModel;
		}

//
//	@Override
//	public String updateCusto(CustomerModel customerModel) {
//		CustomerDomain customer = new CustomerDomain();
//	    
//		   
//		BeanUtils.copyProperties(customerModel, customer);
//		
//		customer.setActive(true);
//		
//		String st= customerDao.updateCusto(customer);
//		return st;
//	}
		
	
	@SuppressWarnings("unused")
	@Override
	public String resetPassword(CustomerModel customerModel) {
		try {
		CustomerDomain customer = new CustomerDomain();
			BeanUtils.copyProperties(customerModel, customer);
			customer = customerDao.isUserExist(customer);
			System.out.println(customer.getEmail());
			
		
			
			
				
			
			if (customer != null) {
		
				String email=customer.getEmail();
				String pass=CommonUtils.generateRandomId();
				customerDao.resetpassword(email,pass);
				 Mail mail = new Mail();
			        mail.setFrom("yenugubala.hari@gmail.com");
			        mail.setTo(customer.getEmail());
			        mail.setSubject("Password Reset Confirmation");

			        Map<String, Object> model = new HashMap<String, Object>();
			        model.put("name", customer.getCustomerName());
			        
			        model.put("signature", Constants.POST_URL+"/v1/resettoken?email="+customer.getEmail()+"&validate="+customer.getValitateCode());
			        mail.setModel(model);
			        
			        MimeMessage message = emailSender.createMimeMessage();
			        MimeMessageHelper helper = new MimeMessageHelper(message,
			                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
			                StandardCharsets.UTF_8.name());

			       // helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));

			        Context context = new Context();
			        context.setVariables(mail.getModel());
			        String html = templateEngine.process("ResetPassword", context);

			        helper.setTo(mail.getTo());
			        helper.setText(html, true);
			        helper.setSubject(mail.getSubject());
			        helper.setFrom(mail.getFrom());

			        emailSender.send(message);
				String status=StatusCode.SUCCESS.name();
				
				return status;
			} 
			else 
			return StatusCode.ERROR.name();
			

				} catch (Exception e) {
			logger.error("Exception in resetPassword:", e);
			return StatusCode.ERROR.name();
		}




}

	@Override
	public void resetPass(String email, String pass) {
		
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password=passwordEncoder.encode(pass);
		
	customerDao.resetpass(email, password);
		
	}








	@Override
	public void createAdmin(CustomerDomain user) {
		// TODO Auto-generated method stub
		
		BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword())); 
		Role userRole = rr.findByName("ROLE_ADMIN");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		user.setActive(true);
		cr.save(user);
		
	}




	@Override
	public Response addCustomer1(CustomerModel customerModel, Model model1) {
		// TODO Auto-generated method stub
		Response response = CommonUtils.getResponseObject("Add customer data");
		try {
			
		Role rl=new Role();
		rl.setName(customerModel.getRolename());
		rr.save(rl);
		response.setStatus(StatusCode.SUCCESS.name());
		
		}
		catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception in addCustomer", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
			
		}
		return response;
	}
	}

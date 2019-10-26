package com.elephant.service.customer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.elephant.constant.Constants;
import com.elephant.constant.StatusCode;
import com.elephant.dao.customer.CustomerDao;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.dao.role.RoleRepository;
import com.elephant.domain.address.AddressDomain;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.roles.Role;
import com.elephant.mapper.customer.CustomerMapper;
import com.elephant.model.customer.CustomerModel;
import com.elephant.model.mail.Mail;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;
import com.elephant.utils.SmtpMailSender;




@Service("customerservice")
public class CustomerServiceImpl implements CustomerService {
	  
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	CustomerMapper customerMapper;
	
	@Autowired
	SmtpMailSender smtpMailSender;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	CustomerRepository cr;
	
	@Autowired
	EntityManager em;
	
	@Autowired
	private RoleRepository roleRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;
	
	
	     //========================add customer=============================//
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	
	@Override
	public Response addCustomer(CustomerModel customerModel,HttpServletRequest request) throws Exception {
		Response response=CommonUtils.getResponseObject("Add Customer");
		CustomerDomain customer = new CustomerDomain();  
		try {
			CustomerDomain custDomain = cr.findByEmail(customerModel.getEmail());
			if(custDomain == null) {
					BeanUtils.copyProperties(customerModel, customer);
					customer.setValitateCode(CommonUtils.generateRandomId());
				    customer.setActive(false);
				    
				    Role userRole=roleRepository.findByName(Constants.ROLE_USER);
				    List<Role> roles = new ArrayList<>();
					roles.add(userRole);
					customer.setRoles(roles);
		
				    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String hashedPassword = passwordEncoder.encode(customerModel.getPassword());
				
					customer.setPassword(hashedPassword );
					
					
					//Mail
					Mail mail = new Mail();
					mail.setFrom(Constants.FROM_ADDRESS);
			        mail.setTo(customer.getEmail());
			        mail.setSubject("Hello  "+customer.getCustomerName());
					
			        Map<String, Object> model = new HashMap<String, Object>();
			        model.put("name", customer.getCustomerName());
			        model.put("email", customer.getEmail());
			        model.put("code", customer.getValitateCode());
				    //request.getServerName()+":"+request.getServerPort()  Constants.BACKEND_URL
				    model.put("signature",Constants.BACKEND_URL +"/v1/confirm?email="+customer.getEmail()+"&validate="+customer.getValitateCode());
				    mail.setModel(model);
				    
				    Context context = new Context();
			        context.setVariables(mail.getModel());
			        
			        String html = templateEngine.process("registration-form", context);
			        
				    smtpMailSender.sendMail(mail, html);
				    System.out.println("mail send successfully"); 
				    
				    response = customerDao.addCustomer(customer);
				    
					response.setMessage("Registration successful. Please validate the email id which sent on your email");
					
			} else {
				response.setStatus(StatusCode.ERROR.name());
				response.setMessage("Email id already exist");
				return response;
			}
	            
		 } 
	
	   catch (AuthenticationException ex) {
		   response.setStatus(StatusCode.ERROR.name());
		   response.setMessage("Exception: "+ex);
       } 
		
		catch(MailSendException exce) {
			response.setStatus(StatusCode.ERROR.name());
			response.setMessage("Exception: " +exce);
		}
	    catch(Exception e) {
			response.setStatus(StatusCode.ERROR.name());
		 	response.setMessage("Exception: " +e);
		}

		return response;
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
	public CustomerModel getCustomerById(long customersId) throws Exception {
		try {
			CustomerModel customerModel = new CustomerModel();
			CustomerDomain customerDomain= customerDao.getCustomer(customersId);
			for (int i = 0; i < customerDomain.getAddressDomain().size(); i++) {
				customerDomain.getAddressDomain().get(i).setCustomerDomain(null);
				customerDomain.getAddressDomain().get(i).setOrderDomain(null);
				customerDomain.getAddressDomain().get(i).setInvoiceDomain(null);
			}

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
		Response response=CommonUtils.getResponseObject("Delete Customer");
		try {
			 response =  customerDao.deleteCustomer(customersId);
		} catch (Exception e) {
			logger.info("Exception deleteCustomer:", e.getMessage());
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
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
	public List<CustomerModel> getcustomerByrollId(int rollId) {
		
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
		
	
	@Override
	public String resetPassword(String email) {
		try 
		{
			CustomerDomain customer = new CustomerDomain();
			customer = customerDao.isUserExist(email);
			if (customer != null) {
				String pass=CommonUtils.generateRandomId();
				customerDao.resetpassword(email,pass);
				
				//Mail
				Mail mail = new Mail();
			    mail.setFrom(Constants.FROM_ADDRESS);
			    mail.setTo(customer.getEmail());
			    mail.setSubject("Password Reset Confirmation");

			    Map<String, Object> model = new HashMap<String, Object>();
			    model.put("name", customer.getCustomerName());
		        model.put("signature", Constants.BACKEND_URL+"/v1/resettoken?email="+customer.getEmail()+"&validate="+customer.getValitateCode());
		        mail.setModel(model);
			        
			    // helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));

			    Context context = new Context();
			    context.setVariables(mail.getModel());
			    String html = templateEngine.process("ResetPassword", context);
			    smtpMailSender.sendMail(mail, html); 

				String status=StatusCode.SUCCESS.name();
				
				return status;
		} 
		else 
		return StatusCode.ERROR.name();
	} 
		catch (Exception e) {
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
		Role userRole = roleRepository.findByName("ROLE_ADMIN");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		user.setActive(true);
		cr.save(user);
		
	}


	@Override
	public Response addRoleToCustomer(CustomerModel customerModel) {
		// TODO Auto-generated method stub
		Response response = CommonUtils.getResponseObject("Add Role data");
		try {
			Role rl=new Role();
			rl.setName(customerModel.getRolename());
			roleRepository.save(rl);
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

	@Override
	public void addAdmin(String email, String password, String name) {
		// TODO Auto-generated method stub
		CustomerDomain customerDomain=cr.findByEmail(email);
		if(customerDomain==null) {
			CustomerDomain cd=new CustomerDomain();
			Role userRole=roleRepository.findByName(Constants.ROLE_ADMIN);
		    List<Role> roles = new ArrayList<>();
		    roles.add(userRole);
		    cd.setRoles(roles);
			cd.setEmail(email);
			cd.setActive(true);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			cd.setPassword(hashedPassword);
			cd.setCustomerName(name);
			cr.save(cd);
		}
		
	}

	@Override
	public void addRole(String roleName) {
		// TODO Auto-generated method stub
		
		Role role=roleRepository.findByName(roleName);
		if(role==null) {
			Role newRole=new Role();
			newRole.setName(roleName);
			roleRepository.save(newRole);
		}
		
	}
	
	@Override
	public boolean checkPassword(CustomerDomain customerDomain,String password) {
		boolean isValid = false;
		if(customerDomain != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			isValid = passwordEncoder.matches(password,customerDomain.getPassword());
		}
		return isValid;
	}


	@Override
	public CustomerModel getCustomerByEmail(String email) {
		CustomerModel customerModel = new CustomerModel();
        if(null != email) {
        	CustomerDomain customerDomain = cr.findByEmail(email);
        	if(customerDomain != null) {
        		BeanUtils.copyProperties(customerDomain, customerModel);
        	}
        }
		
		return customerModel;
	}

	@Override
	public Integer noOfNewCustomers() {
		// TODO Auto-generated method stub
		return customerDao.getNoOfNewCustomers();
	}

	@Override
	public Integer noOfActiveCustomers() {
		// TODO Auto-generated method stub
		return customerDao.getNoOfActiveCustomers();
	}

	@Override
	public ByteArrayInputStream customerExportToExcel() {
		List<CustomerModel> customerModelList = null;
		String[] columnshead = {"Customer Name","Email","Gender","Mobile Number","Active/Inactive"};
		try {
			customerModelList = getcustomerByrollId(2);
			
			//Blank workbook
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        
	        //Create a blank sheet
	        XSSFSheet sheet = workbook.createSheet("Customer Data");
	        
	        Font headerFont = workbook.createFont();
	        headerFont.setBold(true);
	        headerFont.setFontHeightInPoints((short) 11);
	        headerFont.setColor(IndexedColors.BLACK.getIndex());
	        headerFont.setFontName("Arial");
	        
	        CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFont(headerFont);
	        
	        Row headerRow = sheet.createRow(0);
	       
	        for(int i = 0; i < columnshead.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(columnshead[i]);
	            cell.setCellStyle(headerCellStyle);
	        }
	        
	        for(int i = 0; i < columnshead.length; i++) {
	            sheet.autoSizeColumn(i);
	        }
	        
	        Row row = null;
	        Cell rowCell = null;

	        for(int count=0;count<customerModelList.size();count++) {
	        	row = sheet.createRow(count+1);
	        	for(int j=0;j<columnshead.length;j++) {
	        		rowCell = row.createCell(j);
	        		switch(j+1) {
	        			case 1: rowCell.setCellValue(customerModelList.get(count).getCustomerName());
	        					break;
	        			case 2: rowCell.setCellValue(customerModelList.get(count).getEmail());
								break;
	        			case 3: rowCell.setCellValue(customerModelList.get(count).getGender());
								break;
	        			case 4: rowCell.setCellValue(customerModelList.get(count).getMobileNumber());
								break;
	        			case 5: rowCell.setCellValue(customerModelList.get(count).isActive());
								break;
	        		}
	        	}
	        }
	        
	        workbook.write(out);
	        workbook.close();
	        return new ByteArrayInputStream(out.toByteArray());
		}catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
}

package com.elephant.mapper.customer;


	
	import org.springframework.stereotype.Component;

	import com.elephant.domain.customer.CustomerDomain;
	import com.elephant.model.customer.CustomerModel;
	import com.elephant.mapper.AbstractModelMapper;


	@Component
	public class CustomerMapper extends  AbstractModelMapper<CustomerModel, CustomerDomain>   {
		
		

		@Override
		public Class<CustomerModel> entityType() {
			// TODO Auto-generated method stub
			return CustomerModel.class;
		}

		@Override
		public Class<CustomerDomain> modelType() {
			// TODO Auto-generated method stub
			return CustomerDomain.class;
		}

}

package com.elephant.domain.category;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.elephant.domain.categorymenu.CategoryMenuDomain;
import com.elephant.domain.uploadproduct.ProductDomain;


@Entity
//@Table(name="category")@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@Indexed(interceptor=IndexWhenActiveIntercep) @Analyzer(impl = org.apache.lucene.analysis.standard.StandardAnalyzer.class)
//@AnalyzerDef(
//	name="appAnalyzer",
//	charFilters={ @CharFilterDef(factory=HTMLStripCharFilterFactory.class) },
//	tokenizer=@TokenizerDef(factory=StandardTokenizerFactory.class),
//	filters={ 
//		@TokenFilterDef(factory=StandardFilterFactory.class),
//		 @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
//		@TokenFilterDef(factory=StopFilterFactory.class),
//		 @TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = { @Parameter(name = "maxGramSize", value = "15") }),
//		
//		 @TokenFilterDef(factory=SnowballPorterFilterFactory.class, params = {
//			@Parameter(name="language", value="English")
//			
//		})
//	}
//)

public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String categoryId;
	@Column(name="categoryName",unique=true)
	private String categoryName;
	
	@Column(name="description",length = 5000)
	private String description;
	private String createdDate;
	private String modifiedDate;
	private boolean isActive;
	 //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date startingDateAndTime;
	private Date endingDateAndTime;
	
	public Date getStartingDateAndTime() {
		return startingDateAndTime;
	}
	public Date getEndingDateAndTime() {
		return endingDateAndTime;
	}
	public void setStartingDateAndTime(Date dateStart) {
		this.startingDateAndTime = dateStart;
	}
	public void setEndingDateAndTime(Date dateEnd) {
		this.endingDateAndTime = dateEnd;
	}
	
	
	@OneToMany(mappedBy="category",cascade=CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval=true)
	/*private List<UploadProductDomain> product = new ArrayList<>();
	
	public List<UploadProductDomain> getProduct() {
		return product;
	}
	public void setProduct(List<UploadProductDomain> product) {
		this.product = product;
	}*/
	private Set<ProductDomain> product = new HashSet<ProductDomain>();
	
	@ManyToOne
	@JoinColumn(name="category_Menu_Id")
	private CategoryMenuDomain  categoryMenuDomain;

	
	public Set<ProductDomain> getProduct() {
		return product;
	}
	public void setProduct(Set<ProductDomain> product) {
		this.product = product;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public String getDescription() {
		return description;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Set<ProductDomain> setActive(boolean isActive) {
		this.isActive = isActive;
		return product;
	}

	public CategoryMenuDomain getCategoryMenuDomain() {
		return categoryMenuDomain;
	}
	public void setCategoryMenuDomain(CategoryMenuDomain categoryMenuDomain) {
		this.categoryMenuDomain = categoryMenuDomain;
	}
	public Category(){}
	
}

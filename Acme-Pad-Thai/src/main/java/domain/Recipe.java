package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Recipe extends DomainEntity{
	
	//Attributes
	private String ticker;
	private String title;
	private String summary;
	private Date momentAuthored;
	private Date momentLastUpdated;
	private Collection<String> pictures;
	private Collection<String> hints;
	
	//Constructors
	public Recipe(){
		super();
	}
	//Getters and setters
	
	@NotBlank
	@NotNull
	@Pattern(regexp = "^[0-9]{6}-[a-zA-Z]{4}$")
	@Column(unique=true)
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	@NotBlank
	@NotNull
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	@NotNull
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMomentAuthored() {
		return momentAuthored;
	}

	public void setMomentAuthored(Date momentAuthored) {
		this.momentAuthored = momentAuthored;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMomentLastUpdated() {
		return momentLastUpdated;
	}

	public void setMomentLastUpdated(Date momentLastUpdated) {
		this.momentLastUpdated = momentLastUpdated;
	}

	@Valid
	@ElementCollection
	public Collection<String> getPictures() {
		return pictures;
	}

	public void setPictures(Collection<String> pictures) {
		this.pictures = pictures;
	}
	
	public void addPicture(String picture){
		pictures.add(picture);
	}
	
	public void removePicture(String picture){
		pictures.remove(picture);
	}

	@ElementCollection
	public Collection<String> getHints() {
		return hints;
	}

	public void setHints(Collection<String> hints) {
		this.hints = hints;
	}
	
	public void addHint(String hint){
		hints.add(hint);
	}
	
	public void removePHint(String hint){
		hints.remove(hint);
	}
	
	//Relationships
	private Collection<Category> categories;
	private Collection<Quantity> quantities;
	private User user;
	private Collection<Step> steps;
	private Collection<Comment> comments;
	private Collection<LikeSA> likesSA;

	@NotEmpty
	@ManyToMany(mappedBy = "recipes")
	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}
	
	public void addCategory(Category category){
		categories.add(category);
		category.getRecipes().add(this);
	}
	
	public void removeCategory(Category category){
		categories.remove(category);
		category.getRecipes().remove(this);
	}

	@NotEmpty
	@OneToMany(mappedBy = "recipe")
	public Collection<Quantity> getQuantities() {
		return quantities;
	}

	public void setQuantities(Collection<Quantity> quantities) {
		this.quantities = quantities;
	}
	
	public void addQuantity(Quantity quantity){
		quantities.add(quantity);
		quantity.setRecipe(this);
	}
	
	public void removeQuantity(Quantity quantity){
		quantities.remove(quantity);
		quantity.setRecipe(null);
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Step> getSteps() {
		return steps;
	}

	public void setSteps(Collection<Step> steps) {
		this.steps = steps;
	}
	
	public void addStep(Step step){
		steps.add(step);
	}
	
	public void removeStep(Step step){
		steps.remove(step);
	}

	@OneToMany(mappedBy = "recipe")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	public void addComment(Comment comment){
		comments.add(comment);
		comment.setRecipe(this);
	}
	
	public void removeComment(Comment comment){
		comments.remove(comment);
		comment.setRecipe(null);
	}

	@OneToMany(mappedBy = "recipe")
	public Collection<LikeSA> getLikesSA() {
		return likesSA;
	}

	public void setLikesSA(Collection<LikeSA> likesSA) {
		this.likesSA = likesSA;
	}
	
	public void addLike(LikeSA likeSA){
		likesSA.add(likeSA);
		likeSA.setRecipe(this);
	}
	
	public void removeLike(LikeSA likeSA){
		likesSA.remove(likeSA);
		likeSA.setRecipe(null);
	}
	
	
}

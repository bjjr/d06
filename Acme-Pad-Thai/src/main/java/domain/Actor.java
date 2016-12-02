package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	// Attributes
	private String name;
	private String surname;
	private String email;
	private String phone;
	private String postalAddress;

	// Constructors
	public Actor() {
		super();
	}

	// Getters and Setters
	@NotBlank
	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@NotNull
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@NotBlank
	@NotNull
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Pattern(regexp = "^(\\+[1-9]\\d?\\d? )?(\\((?!000)\\d{3}\\) )?(?! )([a-zA-Z0-9]|([ -][a-zA-Z0-9]+)){4,}$")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	// Relationships

	private UserAccount userAccount;
	private Collection<Folder> folders;
	private Collection<Message> sentMessages;
	private Collection<Message> receivedMessages;
	private Collection<MasterClass> masterClasses;
	private Collection<SocialIdentity> socialIdentities;

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@NotEmpty
	@OneToMany(mappedBy = "actor")
	public Collection<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Collection<Folder> folders) {
		this.folders = folders;
	}

	public void addFolder(Folder folder) {
		folders.add(folder);
		folder.setActor(this);
	}

	public void removeFolder(Folder folder) {
		folders.remove(folder);
		folder.setActor(null);
	}

	@OneToMany(mappedBy = "sender")
	public Collection<Message> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(Collection<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public void addSentMessage(Message message) {
		sentMessages.add(message);
		message.setSender(this);
	}

	public void removeSentMessage(Message message) {
		sentMessages.remove(message);
		message.setSender(null);
	}

	@ManyToMany(mappedBy = "recipients")
	public Collection<Message> getReceivedMessages() {
		return receivedMessages;
	}

	public void setReceivedMessages(Collection<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	public void addReceivedMessage(Message message) {
		receivedMessages.add(message);
		message.getRecipients().add(this);
	}

	public void removeReceivedMessage(Message message) {
		receivedMessages.remove(message);
		message.getRecipients().remove(this);
	}

	@ManyToMany(mappedBy = "actors")
	public Collection<MasterClass> getMasterClasses() {
		return masterClasses;
	}

	public void setMasterClasses(Collection<MasterClass> masterClasses) {
		this.masterClasses = masterClasses;
	}

	public void addMasterClass(MasterClass masterClass) {
		masterClasses.add(masterClass);
		masterClass.getActors().add(this);
	}

	public void removeMasterClass(MasterClass masterClass) {
		masterClasses.remove(masterClass);
		masterClass.getActors().remove(this);
	}

	@OneToMany(mappedBy = "actor")
	public Collection<SocialIdentity> getSocialIdentities() {
		return socialIdentities;
	}

	public void setSocialIdentities(Collection<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

	public void addSocialIdentity(SocialIdentity socialIdentity) {
		socialIdentities.add(socialIdentity);
		socialIdentity.setActor(this);
	}

	public void removeSocialIdentity(SocialIdentity socialIdentity) {
		socialIdentities.remove(socialIdentity);
		socialIdentity.setActor(null);
	}

}

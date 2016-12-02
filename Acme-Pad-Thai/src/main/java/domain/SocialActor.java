package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class SocialActor extends Actor{
	
	// Constructors
	
	public SocialActor(){
		super();
	}
	
	// Relationships
	
	private Collection<SocialActor> followers;
	private Collection<SocialActor> follows;
	private Collection<Comment> comments;
	private Collection<LikeSA> likesSA;

	@ManyToMany
	public Collection<SocialActor> getFollowers() {
		return followers;
	}
	
	public void setFollowers(Collection<SocialActor> followers) {
		this.followers = followers;
	}
	
	public void addFollower(SocialActor follower){
		followers.add(follower);
		follower.follows.add(this);
	}
	
	public void removeFollower(SocialActor follower){
		followers.remove(follower);
		follower.follows.remove(this);
	}
	
	@ManyToMany(mappedBy = "followers")
	public Collection<SocialActor> getFollows() {
		return follows;
	}
	
	public void setFollows(Collection<SocialActor> follows) {
		this.follows = follows;
	}
	
	public void followSocialActor(SocialActor socialActor){
		follows.add(socialActor);
		socialActor.followers.add(this);
	}
	
	public void unfollowSocialActor(SocialActor socialActor){
		follows.remove(socialActor);
		socialActor.followers.remove(this);
	}
	
	@OneToMany(mappedBy = "socialActor")
	public Collection<Comment> getComments() {
		return comments;
	}
	
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	public void addComment(Comment comment){
		comments.add(comment);
		comment.setSocialActor(this);
	}
	
	public void removeComment(Comment comment){
		comments.remove(comment);
		comment.setSocialActor(null);
	}
	
	@OneToMany(mappedBy = "socialActor")
	public Collection<LikeSA> getLikesSA() {
		return likesSA;
	}
	
	public void setLikesSA(Collection<LikeSA> likesSA) {
		this.likesSA = likesSA;
	}
	
	public void addLike(LikeSA likeSA){
		likesSA.add(likeSA);
		likeSA.setSocialActor(this);
	}
	
	public void removeLike(LikeSA likeSA){
		likesSA.remove(likeSA);
		likeSA.setSocialActor(null);
	}

}

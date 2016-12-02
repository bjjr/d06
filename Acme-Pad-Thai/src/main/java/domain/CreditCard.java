package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	// Attributes -------------------------------------------------------------
		private String holderName;
		private String brandName;
		private int number;
		private int expirationMonth;
		private int expirationYear;
		private int cvv;
		
		@NotBlank
		@NotNull
		public String getHolderName() {
			return holderName;
		}
		public void setHolderName(String holderName) {
			this.holderName = holderName;
		}
		
		@NotBlank
		@NotNull
		public String getBrandName() {
			return brandName;
		}
		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}
		
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		
		@Range(min = 1, max = 12)
		public int getExpirationMonth() {
			return expirationMonth;
		}
		public void setExpirationMonth(int expirationMonth) {
			this.expirationMonth = expirationMonth;
		}

		public int getExpirationYear() {
			return expirationYear;
		}
		public void setExpirationYear(int expirationYear) {
			this.expirationYear = expirationYear;
		}
		
		@Range(min = 100, max = 999)
		public int getCvv() {
			return cvv;
		}
		public void setCvv(int cvv) {
			this.cvv = cvv;
	}
	
}

package smarthouse.ejb.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import smarthouse.ejb.entity.third.User;
import smarthouse.ejb.util.CryptUtil;

public class PasswordListener {

	/**
	 * Encrypt password before persisting
	 */
	@PrePersist
	@PreUpdate
	public void encryptPassword(Object pc) {
		if (!(pc instanceof User)) {
			return;
		}

		User user = (User) pc;
		if (user.getClearPassword() != null) {
			String password = CryptUtil.sha1(user.getClearPassword());
			user.setPassword(password);
		}
	}
	
}
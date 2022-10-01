package com.me.EzFlight.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.pojo.User;

public class UserDAO extends DAO {

	public boolean userExists(String email) {
		try {
			begin();
			Query q = getSession().createQuery("From User where email=:email");
			q.setString("email", email);
			List list = q.list();
			commit();
			if (list.size() == 0) {
				return false;
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			return false;
		} finally {
			close();
		}
		return true;

	}

	public void addUser(User user) throws FlightException {

		try {
			begin();
			getSession().save(user);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Error while adding user", e);
		} finally {
			close();
		}

	}

	public User getUser(String email, String password) {
		User user = new User();
		try {
			begin();
			Query q = getSession().createQuery("From User where email=:email AND password=:password ");
			q.setString("email", email);
			q.setString("password", password);
			user = (User) q.uniqueResult();
			commit();
			return user;

		} catch (Exception e) {

			System.out.println(e.getMessage());

		} finally {
			close();
		}

		return null;
	}

}

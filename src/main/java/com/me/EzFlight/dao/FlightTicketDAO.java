package com.me.EzFlight.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.pojo.FlightTicket;

public class FlightTicketDAO extends DAO {

	public void create(FlightTicket flightTicket) throws FlightException {
		// TODO Auto-generated method stub
		try {
			begin();
			getSession().save(flightTicket);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Error while adding Ticket", e);
		} finally {
			close();
		}

	}

	public List list() throws FlightException {
		// TODO Auto-generated method stub
		try {
			begin();
			Query q = getSession().createQuery("from FlightTicket");
			List list = q.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Could not list the Tickets", e);
		}

	}

	public List listFlightsByUser(long user_id) throws FlightException {
		// TODO Auto-generated method stub
		try {
			begin();
			Query q = getSession().createQuery("from FlightTicket where user_id=:userId");
			q.setLong("userId", user_id);
			List list = q.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Could not list the Tickets", e);
		}
	}

	public boolean deleteTicketById(long id) throws FlightException {
		// TODO Auto-generated method stub

		try {
			FlightTicket flightTicket = searchTicketById(id);

			if (flightTicket == null) {
				return false;
			}
			begin();
			getSession().delete(flightTicket);
			commit();
			return true;

		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Ticket with following Id couldn'te be deleted", e);

		} finally {
			close();
		}

	}

	public FlightTicket searchTicketById(long id) throws FlightException {
		// TODO Auto-generated method stub
		try {

			begin();
			Query q = getSession().createQuery("from FlightTicket where ticket_id =: ticket_id");
			q.setLong("ticket_id", id);
			FlightTicket flightTicket = (FlightTicket) q.uniqueResult();
			commit();
			return flightTicket;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Ticket with following Id couldn'te be found: " + id + " " + e.getMessage());
		} finally {
			close();
		}

	}

	public boolean checkIfIdExists(long id) {
		// TODO Auto-generated method stub
		try {
			begin();
			Query q = getSession().createQuery("From FlightTicket where ticket_id=:ticketId");
			q.setLong("ticketId", id);
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

}

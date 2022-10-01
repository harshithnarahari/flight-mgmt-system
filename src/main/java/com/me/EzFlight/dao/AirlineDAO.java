package com.me.EzFlight.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.pojo.Airline;

public class AirlineDAO extends DAO {

	public boolean airlineExists(String airlineName) {
		// TODO Auto-generated method stub
		try {
			begin();
			Query q = getSession().createQuery("From Airline where airlineName=:airlineName");
			q.setString("airlineName", airlineName);
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

	public void addAirline(Airline newAirline) throws FlightException {
		// TODO Auto-generated method stub

		try {
			begin();
			getSession().save(newAirline);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Error while adding Airline", e);
		} finally {
			close();
		}

	}

	public List list() throws FlightException {
		// TODO Auto-generated method stub
		try {
			begin();
			Query q = getSession().createQuery("from Airline");
			List list = q.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Could not list the Airlines", e);
		}
	}

	public int updateAirline(long airlineId, String airlineName) throws FlightException {
		// TODO Auto-generated method stub
		try {
			begin();
			Query q = getSession().createQuery("update Airline set airlineName =: airlineName  where airline_id =: id");
			q.setString("airlineName", airlineName);
			q.setLong("id", airlineId);
			int executeUpdate = q.executeUpdate();
			commit();
			return executeUpdate;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException(
					"Airline with following Id couldn'te be updated: " + airlineId + " " + e.getMessage());
		} finally {
			close();
		}

	}

	public Airline searchAirlineById(long id) throws FlightException {
		// TODO Auto-generated method stub
		try {

			begin();
			Query q = getSession().createQuery("from Airline where airline_id =: airline_id");
			q.setLong("airline_id", id);
			Airline airline = (Airline) q.uniqueResult();
			commit();
			return airline;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Airline with following Id couldn'te be found: " + id + " " + e.getMessage());
		} finally {
			close();
		}
	}

	public Airline searchAirlineByName(String name) throws FlightException {
		// TODO Auto-generated method stub
		try {

			begin();
			Query q = getSession().createQuery("from Airline where airlineName =: airlineName");
			q.setString("airlineName", name);
			Airline airline = (Airline) q.uniqueResult();
			commit();
			return airline;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Airline with following name couldn'te be found: " + name + " " + e.getMessage());
		} finally {
			close();
		}
	}

	public boolean deleteById(long id) throws FlightException {
		// TODO Auto-generated method stub
		try {
			Airline airline = searchAirlineById(id);

			if (airline == null) {
				return false;
			}
			begin();
			getSession().delete(airline);
			commit();
			return true;

		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Airline with following Id couldn'te be deleted", e);

		} finally {
			close();
		}

	}

}

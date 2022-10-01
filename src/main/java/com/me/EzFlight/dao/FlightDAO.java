package com.me.EzFlight.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.pojo.Flight;
import com.me.EzFlight.pojo.Location;

public class FlightDAO extends DAO {

	public void addFlight(Flight flight) throws FlightException {
		// TODO Auto-generated method stub
		try {
			begin();
			getSession().save(flight);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Error while adding Flight", e);
		} finally {
			close();
		}
	}

	public boolean flightNameExists(String param) {
		// TODO Auto-generated method stub'
		try {
			begin();
			Query q = getSession().createQuery("From Flight where flightName=:flightName");
			q.setString("flightName", param);
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

	public boolean flightNumberExists(String param) {
		// TODO Auto-generated method stub
		long flightNumber = Long.parseLong(param);
		System.out.println(flightNumber);
		try {
			begin();
			Query q = getSession().createQuery("From Flight where flight_no=:flightNumber");
			q.setLong("flightNumber", flightNumber);
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

	public boolean flightWithDateAndTimeExists(String date, String time, String param) {
		// TODO Auto-generated method stub
		long flightNumber = Long.parseLong(param);
		try {
			begin();
			Query q = getSession().createQuery("From Flight where date=:date and time=:time and flight_no=:flight_no");
			q.setString("date", date);
			q.setString("time", time);
			q.setLong("flight_no", flightNumber);
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

	public List list() throws FlightException {
		// TODO Auto-generated method stub
		try {
			begin();
			Query q = getSession().createQuery("from Flight");
			List list = q.list();
			System.out.println(list.size());
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Could not list the flights", e);
		}

	}

	public boolean deleteById(long flightNo) throws FlightException {
		// TODO Auto-generated method stub
		try {
			Flight flight = searchFlightById(flightNo);

			if (flight == null) {
				return false;
			}
			begin();
			getSession().delete(flight);
			commit();
			return true;

		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Flight with following Id couldn'te be deleted", e);

		} finally {
			close();
		}

	}

	public Flight searchFlightById(long flightNo) throws FlightException {
		// TODO Auto-generated method stub
		try {

			begin();
			Query q = getSession().createQuery("from Flight where flight_no =: flightNo");
			q.setLong("flightNo", flightNo);
			Flight flight = (Flight) q.uniqueResult();
			commit();
			return flight;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException(
					"Flight with following Number couldn'te be found: " + flightNo + " " + e.getMessage());
		} finally {
			close();
		}
	}

	public void updateFlight(Flight flight) throws FlightException {
		// TODO Auto-generated method stub
//	
		try {
			begin();
			getSession().update(flight);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Error while adding Flight", e);
		} finally {
			close();
		}

	}

	public List listCities(String cityname) throws FlightException {
		// TODO Auto-generated method stub
		try {
			begin();
			Criteria city = getSession().createCriteria(Location.class);
			city.add(Restrictions.ilike("locationName", cityname, MatchMode.ANYWHERE));
			List list = city.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException(e.getMessage());
		} finally {
			close();
		}
	}

	public List<Flight> getFlights(long from, String to, String departureDate, String arrivalDate) {
		// TODO Auto-generated method stub
		try {

		} catch (Exception e) {
			begin();
			Query q = getSession().createQuery("From Flight where from =: from");
			q.setLong("from", from);
			List list = q.list();
			commit();
			return list;
		}
		return null;
	}

}

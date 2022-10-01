package com.me.EzFlight.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.pojo.Location;

public class LocationDAO extends DAO {
	public boolean locationExists(String locationName) {
		try {
			begin();
			Query q = getSession().createQuery("From Location where locationName=:locationName");
			q.setString("locationName", locationName);
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

	public void addLocation(Location location) throws FlightException {

		try {
			begin();
			getSession().save(location);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Error while adding Location", e);
		} finally {
			close();
		}

	}

	public List list() throws FlightException {
		try {
			begin();
			Query q = getSession().createQuery("from Location");
			List list = q.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Could not list the locations", e);
		}
	}

	public boolean deleteById(long id) throws FlightException {
		try {
			Location location = searchLocationById(id);

			if (location == null) {
				return false;
			}
			begin();
			getSession().delete(location);
			commit();
			return true;

		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Location with following Id couldn'te be deleted", e);

		} finally {
			close();
		}

	}

	public Location searchLocationByName(String name) throws FlightException {
		try {

			begin();
			Query q = getSession().createQuery("from Location where locationName =: name");
			q.setString("name", name);
			Location location = (Location) q.uniqueResult();
			commit();
			return location;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException(
					"Location with following Name couldn'te be found: " + name + " " + e.getMessage());
		} finally {
			close();
		}

	}

	public Location searchLocationById(long id) throws FlightException {
		try {

			begin();
			Query q = getSession().createQuery("from Location where location_id =: location_id");
			q.setLong("location_id", id);
			Location location = (Location) q.uniqueResult();
			commit();
			return location;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException("Location with following Id couldn'te be found: " + id + " " + e.getMessage());
		} finally {
			close();
		}

	}

	public int updateLocation(long locationId, String newLocationName) throws FlightException {
		System.out.println("DAO " + newLocationName);
		try {
			begin();
			Query q = getSession()
					.createQuery("update Location set locationName =: newLocationName  where location_id =: id");
			q.setString("newLocationName", newLocationName);
			q.setLong("id", locationId);
			int executeUpdate = q.executeUpdate();
			commit();
			return executeUpdate;
		} catch (HibernateException e) {
			rollback();
			throw new FlightException(
					"Location with following Id couldn'te be updated: " + locationId + " " + e.getMessage());
		} finally {
			close();
		}

	}

}

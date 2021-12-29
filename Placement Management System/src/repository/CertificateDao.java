package repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Certificate;
import hibernatecfg.HibernateUtil;


public class CertificateDao {
	
	//Save Certificate Details
	public void saveCertificate(Certificate certificate) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(certificate);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	//Update Certificate Details
	 
	public void updateCertificate(Certificate certificate) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.update(certificate);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	//Delete Certificate Details
	public void deleteCertificate(int id) {

		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// Delete a certificate object
			Certificate certificate = session.get(Certificate.class, id);
			if (certificate != null) {
				session.delete(certificate);
				System.out.println("certificate is deleted");
			}

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	//Display  Specific Certificate Details
	public Certificate getCertificate(int id) {

		Transaction transaction = null;
		Certificate certificate = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an certificate object
			certificate = session.get(Certificate.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return certificate;
	}
	
	//Display  ALL Certificate Details
	@SuppressWarnings("unchecked")
	public List<Certificate> getAllCertificate() {

		Transaction transaction = null;
		List<Certificate> listOfCertificate = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an certificate object
			
			listOfCertificate = session.createQuery("from Certificate").getResultList();
			
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return listOfCertificate;
	}
}

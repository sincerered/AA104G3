package generator;

import java.io.*;
import java.sql.*;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class MyGenerator implements IdentifierGenerator {

	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {

		String prefix = "EE";
		String empno = null;
		Connection con = session.connection();
		try {
			System.out.println("0000000000000000000000000000000000");
			System.out.println(object);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT emp2_seq.NEXTVAL as nextval FROM DUAL");
			rs.next();
			int nextval = rs.getInt("nextval");
			empno = prefix + nextval;
			con.close();
		} catch (SQLException e) {
			throw new HibernateException("Unable to generate Sequence");
		}
		return empno;
	}
}